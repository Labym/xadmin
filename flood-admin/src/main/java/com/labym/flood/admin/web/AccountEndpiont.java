package com.labym.flood.admin.web;

import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.model.vm.LoginVM;
import com.labym.flood.admin.model.vm.RegistrationVM;
import com.labym.flood.admin.model.vm.TokenVM;
import com.labym.flood.admin.service.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void registerAccount(@Valid @RequestBody RegistrationVM registrationDTO) {

        userService.registration(registrationDTO.getLogin(),registrationDTO.getPassword(), UserUtils.accountType(registrationDTO.getLogin()));
    }


    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<TokenVM> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
}
