package com.labym.flood.admin.web;

import com.labym.flood.admin.model.vm.CaptchaVM;
import com.labym.flood.admin.service.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaEndpoint {

    private final CaptchaService captchaService;

    public CaptchaEndpoint(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping
    public ResponseEntity create(){
        CaptchaVM captchaVM = captchaService.genCaptcha();
        return ResponseEntity.ok(captchaVM);
    }
}
