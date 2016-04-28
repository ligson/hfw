package org.ligson.fw.core.common.paramcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ligson on 2015/12/5.
 * 验证类型
 */
public class ValidTypeEnum {
    public static final ValidTypeEnum MOBILE = new ValidTypeEnum(1, "手机号");
    public static final ValidTypeEnum NAME = new ValidTypeEnum(2, "用户名");
    public static final ValidTypeEnum ACCOUNT_ID = new ValidTypeEnum(3, "id");
    public static final ValidTypeEnum PASSWORD = new ValidTypeEnum(4, "账户密码");
    public static final ValidTypeEnum EMAIL = new ValidTypeEnum(5, "邮箱");
    public static final ValidTypeEnum ID_CARD = new ValidTypeEnum(6, "身份证号");

    private int code;
    private String name;

    private static Map<Integer, ValidTypeEnum> codeMap = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ValidTypeEnum.class);

    protected ValidTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
        if (codeMap.containsKey(code)) {
            logger.error("错误代码已经被使用(code:{},msg:{})", code, name);
        }
        codeMap.put(code, this);
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    /***
     * 通过code获取错误码
     *
     * @param code 错误码code
     * @return 错误码
     */
    public static ValidTypeEnum getByCode(String code) {
        return codeMap.get(code);
    }


}
