package com.tutorcenter.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Builder
@Value

public class PaginRes<T> {
    private int page;
	private int itemsPerPage;
	private int total;
	private List<?> addInformation;
	private List<T> data;
}
