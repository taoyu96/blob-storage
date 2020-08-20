package com.bluemyth.storage.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ServiceResult")
public class ServiceResult<T> {

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "结果说明")
    private String message;

    @ApiModelProperty(value = "结果集")
    private T data;

    public static ServiceResult<String> ofSuccess() {
        return ofSuccess("");
    }

    public static <T> ServiceResult<T> ofSuccess(T data) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setMessage(CodeEnum.SUCCEED.getMessage());
        result.setCode(CodeEnum.SUCCEED.getCode());
        result.setData(data);
        return result;
    }

    public static ServiceResult<String> ofFailed(CodeEnum CodeEnum) {
        return ofFailed(CodeEnum, "");
    }

    public static <T> ServiceResult<T> ofFailed(CodeEnum CodeEnum, T data) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setMessage(CodeEnum.getMessage());
        result.setCode(CodeEnum.getCode());
        result.setData(data);
        return result;
    }

    public static ServiceResult<String> ofFailed(String message) {
        return ofFailed(message, "");
    }

    public static ServiceResult<String> ofFailed(String message, String errorMsg) {
        ServiceResult<String> result = new ServiceResult<>();
        result.setMessage(message);
        result.setCode(CodeEnum.FAILED.getCode());
        result.setData(errorMsg);
        return result;
    }

}
