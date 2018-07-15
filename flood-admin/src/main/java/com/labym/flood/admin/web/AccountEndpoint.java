package com.labym.flood.admin.web;

import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.admin.model.dto.UserDTO;
import com.labym.flood.admin.model.vm.LoginVM;
import com.labym.flood.admin.model.vm.RegistrationVM;
import com.labym.flood.admin.model.vm.TokenVM;
import com.labym.flood.admin.service.UserService;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.jwt.JWTConfigurer;
import com.labym.flood.security.jwt.TokenProvider;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountEndpoint {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AccountEndpoint(UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @ApiOperation(
            value = "用户注册"
    )
    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody RegistrationVM registrationDTO) {

        userService.registration(registrationDTO.getLogin(), registrationDTO.getPassword(), UserUtils.accountType(registrationDTO.getLogin()));
    }


    @ApiOperation(
            value = "用户登陆"
    )
    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<TokenVM> authorize(@Valid @RequestBody LoginVM loginVM) {
        String jwt = userService.authorize(loginVM.getUsername(), loginVM.getPassword(), loginVM.getRememberMe().booleanValue());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new TokenVM(jwt), httpHeaders, HttpStatus.OK);
    }


    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @ApiOperation(value = "获取当前用户信息",

            authorizations = {

            @Authorization(
                    value = "petoauth",
                    scopes = {
                            @AuthorizationScope(scope = "add:pet"
                                    , description = "allows adding of pets")
                    }
            )
    }
    )
    @GetMapping("/userinfo")
    @Timed
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new FloodException(StatusCode.INTERNAL_SERVER_ERROR, "User could not be found"));
    }
}
