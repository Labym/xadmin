package com.labym.flood.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class SecurityUser implements UserDetails {

    private final String username;
    private String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final Long id;
    private final Set<GrantedAuthority> authorities;
    public SecurityUser(Long id,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(id,username, password, true, true, true, true, authorities);
    }
    public SecurityUser(Long id,String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.id=id;
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Sets.newHashSet(authorities);
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }




    public boolean equals(Object rhs) {
        return rhs instanceof SecurityUser ? this.username.equals(((SecurityUser)rhs).username) : false;
    }

    public int hashCode() {
        return this.username.hashCode();
    }
}