package com.labym.flood.admin.model.vm;

import lombok.Data;

@Data
public class TokenVM {

    public TokenVM(String token){
        this.token=token;
    }

    private String token;

}
