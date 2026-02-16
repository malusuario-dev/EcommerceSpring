package com.example.Ecomerce.common.infraestructure.filtres;

import com.example.Ecomerce.common.services.JwtServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtServices jwtServie;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("no token found");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization;
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            boolean istokenExpired = jwtServie.isTokenExpired(token);
            boolean canBeRenew = jwtServie.canBeTokenRenew(token);
            if (istokenExpired && !canBeRenew) {
                log.error("token expired");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtServie.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValidToken = jwtServie.isValidToken(token, userDetails);
            if (!isValidToken || SecurityContextHolder.getContext().getAuthentication() != null) {
                log.error("Invalid token or user already aythentificate");
                filterChain.doFilter(request, response);
                return;
            }
            log.info("username is authentificated {}:", username);


            if (istokenExpired && canBeRenew) {
                String renewToken = jwtServie.renewToken(userDetails, token);
                response.setHeader("Authorization", "Bearer " + renewToken);
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception E) {
            log.error("Error validating token {}", E.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, E);
        }
        filterChain.doFilter(request, response);
    }
}
