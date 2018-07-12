package com.labym.flood.admin.service;


import com.google.common.collect.Lists;
import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.admin.model.entity.Account;
import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.repository.AccountRepository;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.SecurityUser;
import com.labym.flood.security.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Transactional(readOnly = true)
@Component("userDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public SecurityUserDetailsService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        AccountType accountType = UserUtils.accountType(login);

        Optional<Account> accountFromDatabase = accountRepository.findOneByLoginAndType(login, accountType);
        return accountFromDatabase
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new FloodException(StatusCode.BAD_REQUEST, "用户名不存在"));


    }

    private SecurityUser createSpringSecurityUser(Account account) {

        User user = account.getUser();
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + account.getLogin() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        });
        return new SecurityUser(user.getId(),account.getLogin(),
                account.getHash(),
                grantedAuthorities);
    }
}

