package com.mxninja.courtMicroServices.oauthService.services;

import com.mxninja.courtMicroServices.oauthService.models.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */
@Component
public class AuthUserService implements UserDetailsService {

    private Map<String, AuthUser> map = new LinkedHashMap<>();

    public AuthUserService() {
        addUser(new AuthUser("mxninja", "mx", new ArrayList<String>() {{
            add("MANAGER");
            add("USER");
        }}));
        addUser(new AuthUser("slifer", "slifer", new ArrayList<String>() {{
            add("USER");
        }}));
    }

    private void addUser(AuthUser authUser) {
        map.put(authUser.getUsername(), authUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return map.get(username);
    }
}
