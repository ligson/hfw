package org.ligson.fw.core.facade.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回异常映射枚举类,数字为5位数，一开头区分
 */
@SuppressWarnings("unused")
public class FailureCodeEnum {

    private static Map<String, FailureCodeEnum> codeMap = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(FailureCodeEnum.class);


    /***
     * -------------------------服务异常------------------------------
     */
    public static final FailureCodeEnum SERVICE_EXCEPTION = new FailureCodeEnum("SERVICE_EXCEPTION", "服务异常");

    /***
     * -------------------------通用参数校验错误码(0开头)------------------------------
     */
    public static final FailureCodeEnum E_PARAM_00001 = new FailureCodeEnum("E_PARAM_00001", "请求参数不能为空");
    public static final FailureCodeEnum E_PARAM_00002 = new FailureCodeEnum("E_PARAM_00002", "请求版本号不能为空");
    public static final FailureCodeEnum E_PARAM_00003 = new FailureCodeEnum("E_PARAM_00003", "字符集不能为空");
    public static final FailureCodeEnum E_PARAM_00004 = new FailureCodeEnum("E_PARAM_00004", "请求参数格式错误");

    /***
     * -------------------------参数校验错误码(1开头)------------------------------
     */
    public static final FailureCodeEnum E_PARAM_10001 = new FailureCodeEnum("E_PARAM_10001", "用户名不能为空");
    public static final FailureCodeEnum E_PARAM_10002 = new FailureCodeEnum("E_PARAM_10002", "用户名格式错误");
    public static final FailureCodeEnum E_PARAM_10003 = new FailureCodeEnum("E_PARAM_10003", "密码不能为空");
    public static final FailureCodeEnum E_PARAM_10004 = new FailureCodeEnum("E_PARAM_10004", "手机号不能为空");
    public static final FailureCodeEnum E_PARAM_10005 = new FailureCodeEnum("E_PARAM_10005", "手机号格式错误");
    public static final FailureCodeEnum E_PARAM_10006 = new FailureCodeEnum("E_PARAM_10006", "邮箱不能为空");
    public static final FailureCodeEnum E_PARAM_10007 = new FailureCodeEnum("E_PARAM_10007", "邮箱格式错误");
    public static final FailureCodeEnum E_PARAM_10008 = new FailureCodeEnum("E_PARAM_10008", "验证码不能为空");
    public static final FailureCodeEnum E_PARAM_10009 = new FailureCodeEnum("E_PARAM_10009", "身份证号不能为空");
    public static final FailureCodeEnum E_PARAM_10010 = new FailureCodeEnum("E_PARAM_10010", "身份证号格式错误");

    /***
     * -------------------------业务检查错误码(2开头)------------------------------
     */
    public static final FailureCodeEnum E_BIZ_20001 = new FailureCodeEnum("E_BIZ_20001", "用户名已存在");
    public static final FailureCodeEnum E_BIZ_20002 = new FailureCodeEnum("E_BIZ_20002", "用户状态无效");
    public static final FailureCodeEnum E_BIZ_20003 = new FailureCodeEnum("E_BIZ_20003", "用户不存在");
    public static final FailureCodeEnum E_BIZ_20004 = new FailureCodeEnum("E_BIZ_20004", "密码错误");
    public static final FailureCodeEnum E_BIZ_20005 = new FailureCodeEnum("E_BIZ_20005", "邮箱已存在");
    public static final FailureCodeEnum E_BIZ_20006 = new FailureCodeEnum("E_BIZ_20006", "手机号已存在");

    /***
     * -------------------------预处理错误码(3开头)------------------------------
     */
    public static final FailureCodeEnum E_PROC_30001 = new FailureCodeEnum("E_PROC_30001", "用户信息转储异常");

    /***
     * -------------------------持久化错误码(4开头)------------------------------
     */
    public static final FailureCodeEnum E_PERSIST_40001 = new FailureCodeEnum("E_PERSIST_40001", "数据持久化异常");


    /**
     * 默认构造
     *
     * @param code 错误代码
     * @param msg  错误信息
     */
    protected FailureCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        if (codeMap.containsKey(code)) {
            logger.error("错误代码已经被使用(code:{},msg:{})", code, msg);
        }
        if (!code.equals("SERVICE_EXCEPTION")) {
            String[] codeArray = code.split("_");
            if (codeArray.length != 3) {
                logger.warn("错误代码格式不正确(正确格式:XX_XX_XXXXX),{}", code);
            } else {
                if (codeArray[2].length() != 5) {
                    logger.warn("错误代码格式不正确,最后数字不是五位,{}", codeArray[2]);
                }
            }
        }
        codeMap.put(code, this);
    }


    /***
     * 通过code获取错误码
     *
     * @param code 错误码code
     * @return 错误码
     */
    public static FailureCodeEnum getByCode(String code) {
        return codeMap.get(code);
    }

    private String msg;

    private String code;

    /***
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getMsg() {
        return msg;
    }

    /***
     * 获取错误代码
     *
     * @return 错误代码
     */
    public String getCode() {
        return code;
    }

}
