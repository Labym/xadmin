package com.labym.flood.admin.service;

import com.labym.flood.admin.common.util.UserUtils;
import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.StatusCode;
import com.labym.flood.admin.model.dto.UserDTO;
import com.labym.flood.admin.model.entity.Account;
import com.labym.flood.admin.model.entity.User;
import com.labym.flood.admin.model.vm.TokenVM;
import com.labym.flood.admin.repository.AccountRepository;
import com.labym.flood.admin.repository.UserRepository;
import com.labym.flood.exception.FloodException;
import com.labym.flood.security.SecurityUtils;
import com.labym.flood.security.jwt.TokenProvider;
import com.labym.flood.util.IDUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(AccountRepository accountRepository, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public void registration(String login, String password, AccountType type) {
        if (accountRepository.existsByLoginAndType(login, type)) {
            throw new FloodException(StatusCode.BAD_REQUEST, String.format("%s(%s)已存在", type.type(), login));
        }
        String username = login;
        if (AccountType.EMAIL.equals(type)) {
            username = username.substring(0, username.indexOf("@"));
        }

        User user = User.builder()
                .username(username)
                .langKey("zh-CN")
                .activated(true)
                .activationKey(IDUtils.uuid()).build();

        userRepository.save(user);

        String salt = IDUtils.uuid();
        Account account = Account.builder()
                .createAt(LocalDateTime.now())
                .hash(passwordEncoder.encode(salt + password))
                .salt(salt)
                .type(type)
                .user(user)
                .login(login).build();


        accountRepository.save(account);

    }

    public String authorize(String username, String password, boolean rememberMe) {

        AccountType accountType = UserUtils.accountType(username);
        Optional<Account> accountFromDatabase = accountRepository.findOneByLoginAndType(username, accountType);
        if (!accountFromDatabase.isPresent()) {
            throw new FloodException(StatusCode.FORBIDDEN, "username not found");
        }
        Account account = accountFromDatabase.get();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, account.getSalt() + password);

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication, rememberMe);
    }

    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findById);
    }

}
