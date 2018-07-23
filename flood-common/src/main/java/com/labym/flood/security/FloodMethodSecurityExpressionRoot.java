package com.labym.flood.security;

import com.labym.flood.security.annotation.Permission;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Method;
import java.util.Optional;

public class FloodMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
        MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;

    private Method method;

    FloodMethodSecurityExpressionRoot(Authentication a) {
        super(a);
    }


    @Override
    public boolean hasPermission(Object target, Object permission) {
        return super.hasPermission(target, permission);
    }


    public boolean hasPermission() {
        if (SecurityUtils.getCurrentUser().map(user -> user.isSystemUser()).orElseThrow(() -> new AccessDeniedException("can't find current user"))) {
            return true;
        }
        Permission permissionAnnotation = method.getAnnotation(Permission.class);
        if(null==permissionAnnotation){
            return true;
        }
        String permission = permissionAnnotation.value();
        return SecurityUtils.currentUserHasPermission(permission);
    }

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    /**
     * Sets the "this" property for use in expressions. Typically this will be the "this"
     * property of the {@code JoinPoint} representing the method invocation which is being
     * protected.
     *
     * @param target the target object on which the method in is being invoked.
     */
    void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
