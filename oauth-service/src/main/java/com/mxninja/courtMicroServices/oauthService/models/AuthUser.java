package com.mxninja.courtMicroServices.oauthService.models;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */
public class AuthUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private List<String> roles;

    public AuthUser(String id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public AuthUser(DefaultClaims defaultClaims) {
        this.id = defaultClaims.getId();
        this.username = defaultClaims.getIssuer();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    public List<String> getRoles() {
        return roles;
    }
}
