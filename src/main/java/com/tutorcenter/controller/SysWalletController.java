package com.tutorcenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sysWallet")
public class SysWalletController {
    // @PreAuthorize("hasAnyAuthority('manager:read','manager:update')")
}
