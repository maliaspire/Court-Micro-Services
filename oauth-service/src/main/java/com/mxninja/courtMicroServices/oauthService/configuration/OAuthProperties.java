package com.mxninja.courtMicroServices.oauthService.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("cms.oauth.config")
@Data
@Component
public class OAuthProperties {


    private String clintId;
    private String clientSecret;
    private String grantTypePassword;
    private String authorizationCode;
    private String refreshToken;
    private String implicit;
    private String scopeRead;
    private String scopeWrite;
    private String trust;
    private int accessTokenValiditySeconds;
    private int refreshTokenValiditySeconds;

    private String name;
    private String jwtSecret;


}
