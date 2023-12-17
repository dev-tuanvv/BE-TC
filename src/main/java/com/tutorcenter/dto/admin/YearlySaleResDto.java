package com.tutorcenter.dto.admin;

import java.util.Map;

import lombok.Data;

@Data
public class YearlySaleResDto {
    private int year;
    private Map<String, MonthData> data;
}