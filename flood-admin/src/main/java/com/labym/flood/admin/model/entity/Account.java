package com.labym.flood.admin.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.Constants;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = Constants.TB_PREFIX + "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, updatable = false)
    private String login;
    @Column(length = 128, nullable = false)
    private String hash;
    @Column(length = 36)
    private String salt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 10)
    private AccountType type;
    private ZonedDateTime createAt;


}
