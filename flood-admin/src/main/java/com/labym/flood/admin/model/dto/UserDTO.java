package com.labym.flood.admin.model.dto;

import com.labym.flood.admin.model.entity.User;
import lombok.Data;

import java.time.Instant;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private String langKey;

    private String imageUrl;

    private Instant resetAt = null;

    public UserDTO (User user){
        this.activated=user.isActivated();
        this.email=user.getEmail();
        this.langKey=user.getLangKey();
        this.username=user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.imageUrl=user.getImageUrl();
        this.id=user.getId();
        this.resetAt=user.getResetAt();
    }
}
