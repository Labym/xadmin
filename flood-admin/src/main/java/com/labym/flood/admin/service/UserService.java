package com.labym.flood.admin.service;

import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.admin.model.entity.Account;
import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.repository.AccountRepository;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.util.IDUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class UserService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public UserService(AccountRepository accountRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;




    @Transactional
    public void registration(String login, String password,AccountType type){
        if (accountRepository.existsByLoginAndType(login,type)) {
            throw new FloodException(StatusCode.BAD_REQUEST,String.format("%s(%s)已存在",type.type(),login));
        }
        String username=login;
        if(AccountType.EMAIL.equals(type)){
            username=username.substring(0,username.indexOf("@"));
        }

        User user = User.builder()
                .username(username)
                .langKey("zh-CN")
                .activated(true)
                .activationKey(IDUtils.uuid()).build();

        userRepository.save(user);

        String salt=IDUtils.uuid();
        Account account = Account.builder()
                .createAt(ZonedDateTime.now())
                .hash(passwordEncoder.encode(salt + password))
                .salt(salt)
                .type(type)
                .user(user)
                .login(login).build();


        accountRepository.save(account);

    }
}
