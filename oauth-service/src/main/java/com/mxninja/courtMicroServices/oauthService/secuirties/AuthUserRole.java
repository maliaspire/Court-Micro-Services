package com.mxninja.courtMicroServices.oauthService.secuirties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 9/12/2018
 *
 * @author Mohammad Ali
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthUserRole {

    String[] roles();

}
