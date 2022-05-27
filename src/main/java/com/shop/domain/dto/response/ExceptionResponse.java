package com.shop.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "익셉션 Response")
public class ExceptionResponse extends BaseResponse {
    private int code;

    public ExceptionResponse(int code, String message) {
        super("fail", message);
        this.code = code;
    }
}
