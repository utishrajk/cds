package com.feisystems.bham.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails springSecurityUser =
                (UserDetails) securityContext
                        .getAuthentication().getPrincipal();

        return springSecurityUser.getUsername();
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        final Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();

        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public static String getIpAddress() {
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
    									.getRequestAttributes()).getRequest();
    	String ipAddress = request.getHeader("X-FORWARDED-FOR");  

    	if (ipAddress == null) {  
    		   ipAddress = request.getRemoteAddr();  
		   }
    	
    	return ipAddress;
    }
    
}
