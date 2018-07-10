package com.labym.flood.admin.service;


import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.admin.model.entity.Account;
import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.repository.AccountRepository;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.UserNotActivatedException;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public DomainUserDetailsService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        AccountType accountType = UserUtils.accountType(login);

        Optional<Account> accountFromDatabase = accountRepository.findOneByLoginAndType(login, accountType);
        return accountFromDatabase
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new FloodException(StatusCode.BAD_REQUEST, "用户名不存在"));


    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(Account account) {

        User user = account.getUser();
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + account.getLogin() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = null;
        return new org.springframework.security.core.userdetails.User(account.getLogin(),
                account.getHash(),
                grantedAuthorities);
    }
}

