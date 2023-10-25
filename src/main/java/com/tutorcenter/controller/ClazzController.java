package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.PaginRes;
import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.service.ClazzService;

@RestController
@RequestMapping("/api/Class")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("")
    public List<Clazz> getAllClazzs() {
        return clazzService.findAll();
    }

    @PostMapping("/search")
    public PaginRes<SearchResDto> search(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc") String order,
            @RequestBody SearchReqDto searchDto) {

        List<SearchResDto> data = clazzService.search(limit, offset, searchDto, order);
        return PaginRes.<SearchResDto>builder().data(data).itemsPerPage(limit).page(offset).build();
    }

}
