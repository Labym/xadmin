package com.labym.flood.admin.web;

import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.model.dto.RegistrationDTO;
import com.labym.flood.admin.service.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountEndpiont {

    private final UserService userService;

    public AccountEndpiont(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegistrationDTO registrationDTO) {

        userService.registration(registrationDTO.getLogin(),registrationDTO.getPassword(), UserUtils.accountType(registrationDTO.getLogin()));
    }
}
