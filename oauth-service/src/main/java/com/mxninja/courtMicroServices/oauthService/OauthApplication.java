package com.mxninja.courtMicroServices.oauthService;

import com.mxninja.courtMicroServices.oauthService.models.AuthUser;
import com.mxninja.courtMicroServices.oauthService.secuirties.JwtProvider;
import com.mxninja.courtMicroServices.oauthService.services.AuthUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */

@SpringBootApplication
public class OauthApplication implements CommandLineRunner {

    private final JwtProvider jwtProvider;
    private final AuthUserService authUserService;

    public OauthApplication(JwtProvider jwtProvider, AuthUserService authUserService) {
        this.jwtProvider = jwtProvider;
        this.authUserService = authUserService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(jwtProvider.generateToken((AuthUser) authUserService.loadUserByUsername("slifer")));
    }
}
