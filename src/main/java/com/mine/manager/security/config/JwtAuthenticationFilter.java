package com.mine.manager.security.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.startsWith("/api/v1/auth");
  }

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = authHeader.substring(7);
      String userEmail = jwtService.extractUsername(jwt);

      if (userEmail != null &&
              SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails =
                this.userDetailsService.loadUserByUsername(userEmail);

        if (jwtService.isTokenValid(jwt, userDetails)) {

          Claims claims = jwtService.extractAllClaims(jwt);

          String role = claims.get("role", String.class);
          List<String> permissions = claims.get("permissions", List.class);

          List<SimpleGrantedAuthority> authorities = new ArrayList<>();

          if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
          }

          if (permissions != null) {
            permissions.forEach(p ->
                    authorities.add(new SimpleGrantedAuthority(p))
            );
          }

          UsernamePasswordAuthenticationToken authToken =
                  new UsernamePasswordAuthenticationToken(
                          userDetails,
                          null,
                          authorities
                  );

          authToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);

    } catch (io.jsonwebtoken.ExpiredJwtException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("""
                  {
                    "error": "TOKEN_EXPIRED",
                    "message": "El token ha expirado"
                  }
              """);
    } catch (io.jsonwebtoken.JwtException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("""
                  {
                    "error": "INVALID_TOKEN",
                    "message": "Token inválido"
                  }
              """);
    }
  }
}
