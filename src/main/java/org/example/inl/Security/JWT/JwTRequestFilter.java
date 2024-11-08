package org.example.inl.Security.JWT;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


@Configuration
@EnableWebSecurity
public class JwTRequestFilter extends OncePerRequestFilter {
    private final JwTUtil jwTUtil;

    public JwTRequestFilter(JwTUtil jwTUtil) {
        this.jwTUtil = jwTUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    try{
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwTUtil.extractedUsername(jwt);
        }

    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if(jwTUtil.validateToken(jwt, username)) {

            Long userId = jwTUtil.extractId(jwt);
            request.setAttribute("userId", userId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
    }
    filterChain.doFilter(request, response);

    } catch (MalformedJwtException e) {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("Invalid JWT token");
    }catch (JwtException e){
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Invalid JWT token. Please login to access this page");
    }
    }
}
