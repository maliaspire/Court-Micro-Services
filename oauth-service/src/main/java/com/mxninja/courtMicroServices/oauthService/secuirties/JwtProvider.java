package com.mxninja.courtMicroServices.oauthService.secuirties;

import com.mxninja.courtMicroServices.oauthService.configuration.OAuthProperties;
import com.mxninja.courtMicroServices.oauthService.models.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Calendar;
import java.util.Date;

/**
 * 9/12/2018
 *
 * @author Mohammad Ali
 */

@Component
public class JwtProvider {

    private final OAuthProperties oAuthProperties;
    private SecretKey key;

    @Autowired
    public JwtProvider(OAuthProperties oAuthProperties) {
        this.oAuthProperties = oAuthProperties;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            key = keyFactory.generateSecret(new PBEKeySpec(oAuthProperties.getJwtSecret().toCharArray()));
        } catch (Exception e) {
            key = null;
            e.printStackTrace();
        }
    }

    @Nullable
    public String generateToken(Authentication authentication) {

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        if (authUser == null) {
            return null;
        }
        return generateToken(authUser);
    }

    @Nullable
    public String generateToken(AuthUser authUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, oAuthProperties.getAccessTokenValiditySeconds());
        return Jwts.builder()
                .setId(authUser.getId())
                .setIssuer(authUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    @Nullable
    public AuthUser getAuthorizedUser(String authorization) {
        try {
            DefaultJws defaultJwt = (DefaultJws) Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);
            DefaultClaims defaultClaims = (DefaultClaims) defaultJwt.getBody();
            return new AuthUser(defaultClaims);
        } catch (Exception e) {
            return null;
        }
    }
}
