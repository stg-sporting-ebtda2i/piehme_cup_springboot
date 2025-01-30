package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.entities.UserDetail;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userId = jwtService.extractUserId(token);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = context.getBean(UserService.class).getUserById(userId);
            UserDetail userDetail = new UserDetail(user, context.getBean(UserService.class));
            if (jwtService.isTokenValid(token, userDetail)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else{
                throw new UnauthorizedAccessException("Token is invalid, unauthorized access");
            }
        }
        filterChain.doFilter(request, response);
    }
}
