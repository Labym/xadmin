package com.labym.flood.admin.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labym.flood.admin.constant.AccountType;
import com.labym.flood.admin.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Constants.TB_PREFIX + "account",
        indexes = {
                @Index(columnList = "userId"),
                @Index(columnList = "login"),
                @Index(columnList = "account_type")
        })
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

    private LocalDateTime createAt;

}
