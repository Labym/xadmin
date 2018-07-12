package com.labym.flood.admin.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labym.flood.admin.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Constants.TB_PREFIX + "user",indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "firstName"),
        @Index(columnList = "lastName"),
        @Index(columnList = "email")
})
public class User {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Size(max = 50)
    @Column(length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 6)
    @Column(length = 6)
    private String langKey;

    @Size(max = 256)
    @Column(length = 256)
    private String imageUrl;

    @Size(max = 32)
    @Column(length = 32)
    @JsonIgnore
    private String activationKey;

    @Size(max = 32)
    @Column(length = 32)
    @JsonIgnore
    private String resetKey;


    private Instant resetAt = null;

    private LocalDateTime createAt;
}
