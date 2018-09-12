package com.mxninja.courtMicroServices.oauthService.configuration;

import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

/**
 * 9/12/2018
 *
 * @author Mohammad Ali
 */

/*@Configuration
@EnableAuthorizationServer*/
public class OAuthAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final OAuthProperties oAuthProperties;

    public OAuthAuthorizationServerConfig(OAuthProperties oAuthProperties) {
        this.oAuthProperties = oAuthProperties;
    }

}
