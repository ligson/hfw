package org.ligson.fw.core.facade.base.result;


import org.ligson.fw.core.facade.enums.ResultCodeEnum;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;

import java.io.Serializable;

/**
 * 通用返回实体
 */
@SuppressWarnings("unused")
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    private String resultCode;
    private String failureCode;
    private String failureMessage;
    private T data;

    public Result() {
    }

    /***
     * 获取成功返回结果
     *
     * @param data 返回结果
     * @param <T>  结果对象
     * @return 对返回结果进行封装
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Result<T> getSuccessResult(T data) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 不用new result的方法
     *
     * @param data   返回结果
     * @param <T>    结果对象
     * @param result 对返回结果进行封装
     * @return 对返回结果进行封装
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Result<T> getSuccessResult(T data, Result result) {

        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 获取成功返回结果
     *
     * @return 对返回结果进行封装
     */
    @SuppressWarnings("rawtypes")
    public static Result getSuccessResult() {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        return result;
    }

    /**
     * 获取失败返回结果
     *
     * @param failureCode 错误码
     * @param <T>         结果对象
     * @return 对返回结果进行封装
     */
    @SuppressWarnings("rawtypes")
    public static <T> Result<T> getFailureResult(String failureCode) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCode);
        return result;
    }

    /***
     * 获取失败返回结果
     *
     * @param failureCode    错误码
     * @param failureMessage 错误信息
     * @param <T>            结果对象
     * @return result
     */
    @SuppressWarnings("rawtypes")
    public static <T> Result<T> getFailureResult(String failureCode, String failureMessage) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCode);
        result.setFailureMessage(failureMessage);
        return result;
    }

    /**
     * 获取失败返回结果
     *
     * @param failureCodeEnum fcode
     * @param <T>             结果对象
     * @return result
     */
    public static <T> Result<T> getFailureResult(FailureCodeEnum failureCodeEnum) {
        Result<T> result = new Result<>();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCodeEnum.getCode());
        result.setFailureMessage(failureCodeEnum.getMsg());
        return result;
    }

    /**
     * 返回接口处理结果
     *
     * @return 接口处理结果
     */
    public T getData() {
        return data;
    }

    /**
     * 返回失败编码
     *
     * @return 失败编码
     */
    public String getFailureCode() {
        return failureCode;
    }

    /**
     * 返回失败原因
     *
     * @return 失败原因
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    /***
     * 判断结果是否成功
     *
     * @return true成功false失败
     */
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getCode().equals(getResultCode());
    }

    @Override
    public String toString() {
        return "Result [resultCode=" + resultCode + ", failureCode="
                + failureCode + ", failureMessage=" + failureMessage
                + ", data=" + data + "]";
    }

}
