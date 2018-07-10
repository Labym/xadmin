package com.labym.flood.admin.model.dto;

import com.labym.flood.config.FloodConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationDTO {

    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "("+FloodConstants.EMAIL_PATTERN +")|("+FloodConstants.PHONE_PATTERN+")",message = "用户名必须是手机号码或者Email")
    private String login;
    @NotNull(message = "密码不能为空")
    @Size(min = 8,max = 20,message = "密码长度必须在8到20之间")
    private String password;
    @NotNull(message = "验证码不能为空")
    @Size(max = 4,min = 4,message = "验证码的长度为4")
    private String captcha;
    @NotNull(message = "验证码的Id不能为空")
    private String captchaId;

}
