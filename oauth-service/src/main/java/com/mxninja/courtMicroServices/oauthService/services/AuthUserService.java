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
        addUser(new AuthUser("9c8e21e4-b665-11e8-aeeb-529269fb1459", "mxninja", "mx", new ArrayList<String>() {{
            add("MANAGER");
            add("USER");
        }}));
        addUser(new AuthUser("cffe6877-6a52-483d-bcff-08c6bc803912", "slifer", "slifer", new ArrayList<String>() {{
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
