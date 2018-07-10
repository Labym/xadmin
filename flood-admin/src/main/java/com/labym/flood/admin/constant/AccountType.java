package com.labym.flood.admin.constant;

import org.springframework.security.core.parameters.P;

public enum AccountType {
    EMAIL,PHONE
    ;

    private String type;

    public String type(){
        return this.type;
    }

    static {
        EMAIL.type="Email";
        PHONE.type="手机";
    }

}
