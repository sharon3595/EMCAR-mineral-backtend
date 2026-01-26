package com.mine.manager.security.auth;

import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.security.config.JwtService;
import com.mine.manager.security.user.Permission;
import com.mine.manager.security.user.User;
import com.mine.manager.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequestDto request) {
    if (repository.existsByUsernameAndStateTrue(request.getUsername())) {
      throw new DuplicateException("Usuario", "nombre", request.getUsername());
    }
    var user = User.builder()
            .name(request.getName())
            .surname(request.getSurname())
            .username(request.getUsername())
            .state(request.getState())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

    repository.save(user);

    return getAuthenticationResponse(user);
  }

  private AuthenticationResponse getAuthenticationResponse(User user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole().name());
    extraClaims.put(
            "permissions",
            user.getRole()
                    .getPermissions()
                    .stream()
                    .map(Permission::getPermission)
                    .toList()
    );
    extraClaims.put("userId", user.getId());
    var jwtToken = jwtService.generateToken(extraClaims, user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    var user = repository.findByUsernameAndStateTrue(request.getUsername()).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

    return getAuthenticationResponse(user);
  }
}