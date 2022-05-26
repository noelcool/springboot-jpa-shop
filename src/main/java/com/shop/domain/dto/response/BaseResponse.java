package com.shop.domain.dto.response;

import com.shop.domain.constant.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private String result;
    private String reason;

    public BaseResponse() {
        this.result = MessageUtils.SUCCESS.name();
        this.reason = "";
    }

    public BaseResponse(String reason) {
        this.result = MessageUtils.FAIL.name();
        this.reason = reason;
    }
}
