package com.labym.flood.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class FloodPermissionEvaluator {

    public FloodPermissionEvaluator(){
        System.out.println("FloodPermissionEvaluator");
    }

    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {


        return true;
    }


    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
