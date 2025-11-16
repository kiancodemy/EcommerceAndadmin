package com.Main.Ecommerce.auth.configurations.filters;
import com.Main.Ecommerce.auth.configurations.JWT.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGenerationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization"); /// or "Authorization" if you prefer "Bearer <token>"

        if (jwt != null && !jwt.isEmpty()) {
            if (!jwtUtils.isTokenExpired(jwt)) { /// check if token is NOT expired
                Claims claims = jwtUtils.extractUsername(jwt);

                /// Extract email and roles
                String email = claims.get("email", String.class);
                List<String> roles = claims.get("roles", List.class);

                /// Convert roles to GrantedAuthority
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                /// Create authentication object
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                /// Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        /// add filter chain to continue
        filterChain.doFilter(request, response);
    }
}
