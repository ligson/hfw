package org.ligson.fw.core.common.paramcheck;


import org.ligson.fw.core.common.model.Context;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;
import org.ligson.fw.core.facade.base.dto.BaseResponseDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.ligson.fw.string.validator.EmailValidator;
import org.ligson.fw.string.validator.IdcardValidator;
import org.ligson.fw.string.validator.PhoneValidator;
import org.springframework.util.StringUtils;

/**
 * 通用参数校验类
 *
 * @author ligson
 */
public class CommonParamCheck {

    /**
     * 请求通用参数检查
     *
     * @param context    上下文
     * @param baseReqDto 请求
     * @return 是否有效
     */
    public static Boolean isValid(Context context, BaseRequestDto baseReqDto) {

        if (baseReqDto == null) {
            context.setResult(Result.getFailureResult(FailureCodeEnum.E_PARAM_00001));
            return false;
        }
        if (StringUtils.isEmpty(baseReqDto.getVersion())) {
            context.setResult(Result.getFailureResult(FailureCodeEnum.E_PARAM_00002));
            return false;
        }
        if (StringUtils.isEmpty(baseReqDto.getCharset())) {
            context.setResult(Result.getFailureResult(FailureCodeEnum.E_PARAM_00003));
            return false;
        }
        return true;
    }

    /***
     * 手机号格式验证
     *
     * @param mobile  x
     * @param context x
     * @return x
     */
    public static <T extends BaseResponseDto> Boolean isValidMobile(String mobile, Context context, Class<T> tClass) {
        if (isNotEmpty(mobile, context, tClass, FailureCodeEnum.E_PARAM_10004)) {
            if (PhoneValidator.isMobile(mobile)) {
                return true;
            } else {
                Result<T> result = Result.getFailureResult(FailureCodeEnum.E_PARAM_10005);
                context.setResult(result);
                return false;
            }
        } else {
            return false;
        }
    }

    /***
     * 用户名检查
     *
     * @param name    用户名
     * @param context context
     * @param tClass  响应的class
     * @param <T>     必须继承BaseResponseDto
     * @return 是否有效
     */
    public static <T extends BaseResponseDto> Boolean isValidName(String name, Context context, Class<T> tClass) {
        //E_PARAM_015
        if (isNotEmpty(name, context, tClass, FailureCodeEnum.E_PARAM_10001)) {
            if (name.length() > 64) {
                Result<T> result = Result.getFailureResult(FailureCodeEnum.E_PARAM_10002);
                context.setResult(result);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /***
     * 身份证格式验证
     *
     * @param idCard  x
     * @param context x
     * @return x
     */
    public static <T extends BaseResponseDto> Boolean isValidIdCard(String idCard, Context context, Class<T> tClass) {
        if (isNotEmpty(idCard, context, tClass, FailureCodeEnum.E_PARAM_10009)) {
            if (IdcardValidator.isValidIdNumber(idCard)) {
                return true;
            } else {
                Result<T> result = Result.getFailureResult(FailureCodeEnum.E_PARAM_10010);
                context.setResult(result);
                return false;
            }
        } else {
            return false;
        }
    }


    /****
     * 空检查
     *
     * @param object          x
     * @param context         x
     * @param tClass          x
     * @param failureCodeEnum x
     * @param <T>             x
     * @return x
     */
    public static <T extends BaseResponseDto> Boolean isNotEmpty(Object object, Context context, Class<T> tClass, FailureCodeEnum failureCodeEnum) {
        if (StringUtils.isEmpty(object) || "null".equals(object)) {
            Result<T> result = Result.getFailureResult(failureCodeEnum.getCode(), failureCodeEnum.getMsg());
            context.setResult(result);
            return false;
        }
        return true;
    }

    /***
     * password
     *
     * @param password password
     * @param context  password
     * @param tClass   t
     * @param <T>      password
     * @return true/false
     */
    public static <T extends BaseResponseDto> Boolean isValidPwd(String password, Context context, Class<T> tClass) {
        return isNotEmpty(password, context, tClass, FailureCodeEnum.E_PARAM_10003);
    }


    /***
     * 通用验证
     *
     * @param typeEnums 验证类型
     * @param values    字符串值
     * @param context   context
     * @param tClass    响应的class
     * @param <T>       必须继承BaseResponseDto
     * @return 是否有效
     */
    public static <T extends BaseResponseDto> Boolean isValid(ValidTypeEnum[] typeEnums, String[] values, Context context, Class<T> tClass) {
        if (typeEnums != null && values != null && typeEnums.length == values.length) {
            for (int i = 0; i < typeEnums.length; i++) {
                ValidTypeEnum validTypeEnum = typeEnums[i];
                String value = values[i];
                //TODO 这个地方是不是可以利用反射再简化一下?
                if (validTypeEnum == ValidTypeEnum.ID_CARD) {
                    if (!isValidIdCard(value, context, tClass)) {
                        return false;
                    }
                } else if (validTypeEnum == ValidTypeEnum.MOBILE) {
                    if (!isValidMobile(value, context, tClass)) {
                        return false;
                    }
                } else if (validTypeEnum == ValidTypeEnum.NAME) {
                    if (!isValidName(value, context, tClass)) {
                        return false;
                    }
                } else if (validTypeEnum == ValidTypeEnum.PASSWORD) {
                    if (!isValidPwd(value, context, tClass)) {
                        return false;
                    }
                } else if (validTypeEnum == ValidTypeEnum.EMAIL) {
                    if (!isValidEmail(value, context, tClass)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static <T extends BaseResponseDto> boolean isValidEmail(String email, Context context, Class<T> dtoClass) {
        if (isNotEmpty(email, context, dtoClass, FailureCodeEnum.E_PARAM_10006)) {
            if (EmailValidator.isValidEmail(email)) {
                return true;
            } else {
                Result<T> responseDtoResult = Result.getFailureResult(FailureCodeEnum.E_PARAM_10007);
                context.setResult(responseDtoResult);
                return false;
            }
        } else {
            return false;
        }
    }
}
