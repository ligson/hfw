package org.ligson.fw.core.facade.base.dto;

import org.apache.commons.lang.StringUtils;
import org.ligson.fw.core.facade.annotation.Param;
import org.ligson.fw.string.validator.DataValidator;
import org.ligson.fw.string.validator.EmailValidator;
import org.ligson.fw.string.validator.PhoneValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 请求实体基础类
 */
@SuppressWarnings("serial")
public class BaseRequestDto extends BaseDto {
    private static final Logger logger = LoggerFactory.getLogger(BaseRequestDto.class);

    private Map<String, String> errorFieldMap = new HashMap<>();
    /**
     * 版本号
     */
    @Param(name = "版本号", required = false)
    private String version = "0.0.0";
    /**
     * 字符集
     */
    @Param(name = "字符集", required = false)
    private String charset = "UTF-8";


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, String> getErrorFieldMap() {
        return errorFieldMap;
    }

    public void setErrorFieldMap(Map<String, String> errorFieldMap) {
        this.errorFieldMap = errorFieldMap;
    }

    @Override
    public String toString() {
        return "BaseRequestDto{" +
                "version='" + version + '\'' +
                ", charset='" + charset + '\'' +
                '}';
    }

    public boolean validate() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object value;
            String name = field.getName();
            List<String> errors = new ArrayList<>();

            try {
                String javaName = name.substring(0, 1).toUpperCase() + name.substring(1);
                String getMethodName = "get" + javaName;
                Method method = getClass().getMethod(getMethodName);
                value = method.invoke(this);
            } catch (Exception e) {
                continue;
            }
            Param param = field.getDeclaredAnnotation(Param.class);
            if (param != null) {
                if (param.required()) {
                    if (value == null) {
                        String errorMsg = "不允许为空";
                        logger.warn("{}不允许为空", name);
                        errors.add(errorMsg);
                    }
                }
                if (value != null) {
                    if (param.email()) {
                        if (!EmailValidator.isValidEmail(value.toString())) {
                            String errorMsg = "不是一个有效的邮箱格式";
                            logger.warn("请求参数{}的值({})不是一个有效的邮箱格式", name, value);
                            errors.add(errorMsg);
                        }
                    }
                    if (param.mobile()) {
                        if (!PhoneValidator.isMobile(value.toString())) {
                            String errorMsg = "不是一个有效的手机号格式";
                            logger.warn("请求参数{}的值({})不是一个有效的手机号格式", name, value);
                            errors.add(errorMsg);
                        }
                    }
                    if (param.integer()) {
                        if (!DataValidator.isIntege(value.toString())) {
                            String errorMsg = "不是一个正整数格式";
                            logger.warn("请求参数{}的值({})不是一个正整数格式", name, value);
                            errors.add(errorMsg);
                        }
                    }
                    if (!StringUtils.isEmpty(param.regexp())) {
                        if (!value.toString().matches(param.regexp())) {
                            String errorMsg = "格式不正确";
                            logger.warn("请求参数{}的值{}与正则({})格式不一致", name, value, param.regexp());
                            errors.add(errorMsg);
                        }
                    }
                    if (param.inList().length != 0) {
                        int idx = Arrays.binarySearch(param.inList(), value);
                        if (idx == -1) {
                            String errorMsg = "不在范围内";
                            logger.warn("请求参数{}的值{}不在范围({})内格式不一致", name, value, Arrays.toString(param.inList()));
                            errors.add(errorMsg);
                        }
                    }
                    if (param.minLen() != -1) {
                        if (value.toString().length() < param.minLen()) {
                            String errorMsg = "长度不能小于" + param.minLen();
                            logger.warn("请求参数{}的值{}的长度不能小于{}", name, value, param.minLen());
                            errors.add(errorMsg);
                        }
                    }
                    if (param.maxLen() != -1) {
                        if (value.toString().length() > param.maxLen()) {
                            String errorMsg = "长度不能大于" + param.maxLen();
                            logger.warn("请求参数{}的值{}的长度不能大于{}", name, value, param.maxLen());
                            errors.add(errorMsg);
                        }
                    }

                    //TODO -1这个边界不对,思路需要重新想想
                    if ((param.min() != -1) || (param.max() != -1)) {
                        if (!DataValidator.isNum(value.toString())) {
                            String errorMsg = "不是一个整数格式";
                            logger.warn("请求参数{}的值({})不是一个整数格式", name, value);
                            errors.add(errorMsg);
                            continue;
                        }
                        int num = Integer.parseInt(value.toString());
                        if (param.min() != -1) {
                            if (num < param.min()) {
                                String errorMsg = "不能小于" + param.min();
                                logger.warn("请求参数{}的值{}不能小于{}", name, value, param.min());
                                errors.add(errorMsg);
                            }
                        }
                        if (param.max() != -1) {
                            if (num > param.max()) {
                                String errorMsg = "不能大于" + param.max();
                                logger.warn("请求参数{}的值{}不能大于{}", name, value, param.max());
                                errors.add(errorMsg);
                            }
                        }
                    }
                }
            }
            if (errors.size() > 0) {
                String errorMsg = "";
                for (int i = 0; i < errors.size(); i++) {
                    String msg = errors.get(i);
                    errorMsg += msg;
                    if (i != errors.size() - 1) {
                        errorMsg += ";";
                    }
                }
                errorFieldMap.put(name, errorMsg);
            }
        }
        return errorFieldMap.size() == 0;
    }
}
