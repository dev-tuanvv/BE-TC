package com.tutorcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.service.SysWalletService;

@RestController
@RequestMapping("/api/sysWallet")
public class SysWalletController {
    @Autowired
    private SysWalletService sysWalletService;

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("")
    public ApiResponseDto<Float> getSystemWallet() {
        return ApiResponseDto.<Float>builder().data(sysWalletService.getBalance()).build();
    }
}
