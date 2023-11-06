package com.tutorcenter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tutorcenter.constant.ResponseCode;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Builder
@Value
public class ApiResponseDto<T> {

    @JsonInclude(Include.NON_NULL)
    private String requestId;

    @Builder.Default
    private String responseCode = ResponseCode.SUCCESS;
    
    @JsonInclude(Include.NON_NULL)
    private T data;

    private String message;

}