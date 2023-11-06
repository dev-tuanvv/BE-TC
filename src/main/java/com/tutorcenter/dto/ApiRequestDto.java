package com.tutorcenter.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Builder
@Value
public class ApiRequestDto<T> {
    @JsonProperty("RequestDateTime")
    private Date requestDateTime = new Date();

    @JsonProperty("RequestID")
    private String requestId = UUID.randomUUID().toString();

    @JsonProperty("Data")
    @Nullable
    private T data;
}