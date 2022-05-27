package com.shop.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Schema(description = "Common Response")
public class CommonResponse<T> extends BaseResponse {

    @Schema(description = "조회 결과")
    private T data;

    public CommonResponse() {
        super();
    }
    public CommonResponse(T data) {
        super();
        this.data = data;
    }
}
