package krupkoillia.chesstracker.userservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    private SecurityUtil() {

    }

    public static Long getAuthenticatedUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        if (!(authentication.getPrincipal() instanceof Long userId)) {
            throw new IllegalStateException("Invalid authentication principal");
        }

        return userId;
    }

}
