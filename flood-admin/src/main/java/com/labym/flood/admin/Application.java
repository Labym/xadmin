package com.labym.flood.admin;


import com.labym.flood.admin.model.entity.Account;
import com.labym.flood.admin.repository.AccountRepository;
import com.labym.flood.config.FloodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;


@RestController
@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class,FloodProperties.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    AccountRepository repository;

    @GetMapping("/test")
    @ResponseBody
    @Transactional
    public Object get(){
        Account account=new Account();
        account.setCreateAt(ZonedDateTime.now());
        account.setHash("123123123");
        account.setLogin("123123123");
        account.setSalt("123123132");
        repository.save(account);

        return repository.findAll();
    }

}
