package com.bluemyth.storage.response;

import lombok.Getter;

@Getter
public enum CodeEnum {

    SUCCEED("0", "成功"),
    FAILED("110", "失败");

    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
