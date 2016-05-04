package org.ligson.fw.core.common.biz;

import org.ligson.fw.core.common.model.Context;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;
import org.ligson.fw.core.facade.base.dto.BaseResponseDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.string.validator.DataValidator;
import org.ligson.fw.string.validator.EmailValidator;
import org.ligson.fw.string.validator.PhoneValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;

/**
 * 抽象业务类
 */
@SuppressWarnings("unused")
public abstract class AbstractBiz<Q extends BaseRequestDto, R extends BaseResponseDto> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBiz.class);
    protected Context context = new Context();
    protected Q requestDto;
    protected R responseDto;

    /**
     * 初始化通用信息：
     */
    private void init() {
        context.setResult(null);
        context.getAttrMap().clear();
        context.setBusinessDate(new Date());
        context.setCurrentDate(new Date());
        before();
    }

    /**
     * 核心业务入口方法
     *
     * @param dto 请求的dto
     */
    public Result<R> operation(Q dto) {
        requestDto = dto;
        setResponseDto();
        return result();
    }

    /**
     * 前置方法
     */
    public abstract void before();

    /**
     * 参数检查
     */
    public abstract Boolean paramCheck();

    /**
     * 业务检查
     */
    public abstract Boolean bizCheck();

    /**
     * 数据预处理
     */
    public abstract Boolean txnPreProcessing();

    /**
     * 数据存储
     */
    public abstract Boolean persistence();

    /**
     * 后置方法
     */
    public abstract void after();

    @SuppressWarnings("unchecked")
    public Result<R> result() {

        try {
            //初始化
            init();
            //交易处理
            operate();

        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            if (context.getResult() == null) {
                setFailureResult(FailureCodeEnum.SERVICE_EXCEPTION);
            }
        } finally {
            Assert.notNull(context.getResult(), "返回结果不能为空");
            try {
                //后置业务处理
                after();
            } catch (Exception e) {
                logger.error("后置业务处理异常.", e);
            }
        }

        return context.getResult();
    }

    @SuppressWarnings("unchecked")
    protected boolean requestParamCheck() {
        Field[] fields = requestDto.getClass().getDeclaredFields();
        Field[] superFields = requestDto.getClass().getSuperclass().getDeclaredFields();
        Field[] allFields = new Field[fields.length + superFields.length];
        System.arraycopy(fields, 0, allFields, 0, fields.length);
        System.arraycopy(superFields, 0, allFields, fields.length, superFields.length);

        for (Field field : allFields) {
            Object value = null;
            String name = field.getName();
            try {
                String javaName = name.substring(0, 1).toUpperCase() + name.substring(1);
                String getMethodName = "get" + javaName;
                Method method = requestDto.getClass().getMethod(getMethodName);
                value = method.invoke(requestDto);
            } catch (Exception e) {
                continue;
            }
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Param) {
                    Param param = (Param) annotation;
                    if (param.required()) {
                        if (value == null) {
                            logger.warn("请求参数{}不允许为空", name);
                            setFailureResult(FailureCodeEnum.E_PARAM_00001, FailureCodeEnum.E_PARAM_00001.getMsg() + ":" + name);
                            return false;
                        }
                    }
                    if (value != null) {
                        if (param.email()) {
                            if (!EmailValidator.isValidEmail(value.toString())) {
                                logger.warn("请求参数{}的值({})不是一个有效的邮箱格式", name, value);
                                setFailureResult(FailureCodeEnum.E_PARAM_10007, FailureCodeEnum.E_PARAM_10007.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (param.mobile()) {
                            if (!PhoneValidator.isMobile(value.toString())) {
                                logger.warn("请求参数{}的值({})不是一个有效的手机号格式", name, value);
                                setFailureResult(FailureCodeEnum.E_PARAM_10005, FailureCodeEnum.E_PARAM_10005.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (param.integer()) {
                            if (!DataValidator.isIntege(value.toString())) {
                                logger.warn("请求参数{}的值({})不是一个正整数格式", name, value);
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (!StringUtils.isEmpty(param.regexp())) {
                            if (!value.toString().matches(param.regexp())) {
                                logger.warn("请求参数{}的值{}与正则({})格式不一致", name, value, param.regexp());
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (param.inList().length != 0) {
                            int idx = Arrays.binarySearch(param.inList(), value);
                            if (idx == -1) {
                                logger.warn("请求参数{}的值{}不在范围({})内格式不一致", name, value, Arrays.toString(param.inList()));
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (param.minLen() != -1) {
                            if (value.toString().length() < param.minLen()) {
                                logger.warn("请求参数{}的值{}的长度不能小于{}", name, value, param.minLen());
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                        }
                        if (param.maxLen() != -1) {
                            if (value.toString().length() > param.maxLen()) {
                                logger.warn("请求参数{}的值{}的长度不能大于{}", name, value, param.maxLen());
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                        }

                        //TODO -1这个边界不对,思路需要重新想想
                        if ((param.min() != -1) || (param.max() != -1)) {
                            if (!DataValidator.isNum(value.toString())) {
                                logger.warn("请求参数{}的值({})不是一个整数格式", name, value);
                                setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                return false;
                            }
                            int num = Integer.parseInt(value.toString());
                            if (param.min() != -1) {
                                if (num < param.min()) {
                                    logger.warn("请求参数{}的值{}不能小于{}", name, value, param.min());
                                    setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                    return false;
                                }
                            }
                            if (param.max() != -1) {
                                if (num > param.max()) {
                                    logger.warn("请求参数{}的值{}不能大于{}", name, value, param.max());
                                    setFailureResult(FailureCodeEnum.E_PARAM_00004, FailureCodeEnum.E_PARAM_00004.getMsg() + ":" + name);
                                    return false;
                                }
                            }
                        }

                    }
                }

            }


        }
        return true;
    }

    /**
     * 业务处理
     */
    private void operate() {
        logger.debug("======================>基本参数检查开始");
        boolean requestParamCheck = requestParamCheck();
        if (!requestParamCheck) {
            logger.debug("======================>基本参数检查失败,开始返回");
            return;
        }
        logger.debug("======================>基本参数检查成功");

        logger.debug("======================>参数检查开始");
        Boolean paramCheck = paramCheck();
        if (paramCheck != null && !paramCheck) {
            logger.debug("======================>参数检查失败,开始返回");
            return;
        }
        logger.debug("======================>参数检查成功");
        logger.debug("======================>业务检查开始");
        Boolean bizCheck = bizCheck();
        if (bizCheck != null && !bizCheck) {
            logger.debug("======================>业务检查失败,开始返回");
            return;
        }
        logger.debug("======================>业务检查成功");
        logger.debug("======================>业务处理开始");
        Boolean txnPreProcessing = txnPreProcessing();
        if (txnPreProcessing != null && !txnPreProcessing) {
            logger.debug("======================>业务处理完成,开始返回");
            return;
        }
        //忘记调用setSuccessResult的再调用一次
        if ((context.getResult() == null) && (responseDto.getSuccess() != null && responseDto.getSuccess())) {
            setSuccessResult();
        }
        logger.debug("======================>业务处理完成");
        logger.debug("======================>持久化开始");
        Boolean persistence = persistence();
        if (persistence != null && !persistence) {
            logger.debug("======================>持久化完成,开始返回");
            return;
        }
        logger.debug("======================>持久化完成");
    }


    @SuppressWarnings("rawtypes")
    private Class getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();
        logger.debug("=================>处理请求:{}", genType.getTypeName());
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public void setSuccessResult() {
        Result<R> responseDtoResult = Result.getSuccessResult(responseDto);
        context.setResult(responseDtoResult);
        responseDto.setSuccess(true);
    }

    public void setFailureResult(FailureCodeEnum failureCodeEnum) {
        Result<R> result = Result.getFailureResult(failureCodeEnum);
        context.setResult(result);
    }

    public void setFailureResult(FailureCodeEnum failureCodeEnum, String failureMessage) {
        Result<R> result = Result.getFailureResult(failureCodeEnum.getCode(), failureMessage);
        context.setResult(result);
    }

    @SuppressWarnings("unchecked")
    private void setResponseDto() {
        Class<R> rCls = getGenericType(1);
        try {
            responseDto = rCls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
