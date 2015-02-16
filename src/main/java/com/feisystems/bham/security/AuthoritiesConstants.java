package com.feisystems.bham.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    public static final String ADMIN = "ADMIN";

    public static final String USER = "USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    public static final String ROLE_USER = "ROLE_USER";
}
