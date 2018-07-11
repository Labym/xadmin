package com.labym.flood.admin.model.vm;


import lombok.Data;

@Data
public class LoginVM {

    private String username;
    private String password;
    private Boolean rememberMe=false;
    private String captcha;
}
