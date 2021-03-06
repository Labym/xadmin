package com.labym.flood.admin.repository;

import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByLoginAndType(String login, AccountType type);


    Optional<Account> findOneByLoginAndType(String login,AccountType type);
}
