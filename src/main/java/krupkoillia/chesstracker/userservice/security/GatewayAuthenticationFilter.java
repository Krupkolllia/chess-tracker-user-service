package krupkoillia.chesstracker.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class GatewayAuthenticationFilter extends OncePerRequestFilter {

    private static final String USER_ID_HEADER_NAME = "X-User-Id";

    private static final String GATEWAY_SECRET_HEADER_NAME = "X-Gateway-Secret";

    @Value("${security.gateway.secret}")
    private String gatewaySecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String userIdFromHeader = request.getHeader(USER_ID_HEADER_NAME);
        String gatewaySecretFromHeader = request.getHeader(GATEWAY_SECRET_HEADER_NAME);

        if (!Objects.equals(gatewaySecretFromHeader, gatewaySecret)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (userIdFromHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdFromHeader);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = new PreAuthenticatedAuthenticationToken(
                userId, null, List.of()
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);

    }
}
