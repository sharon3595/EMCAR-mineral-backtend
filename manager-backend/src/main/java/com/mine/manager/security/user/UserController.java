package com.mine.manager.security.user;

import com.mine.manager.security.auth.AuthenticationResponse;
import com.mine.manager.security.auth.AuthenticationService;
import com.mine.manager.security.auth.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuarios")
    public ResponseEntity<AuthenticationResponse> register(@Validated(RegisterRequestDto.OnCreate.class) @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios.")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID.")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario por ID.")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Integer id,
            @Validated(RegisterRequestDto.OnUpdate.class) @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario (Borrado lógico).")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reset-password/{id}")
    public ResponseEntity<String> forcePasswordReset(
            @PathVariable Integer id,
            @RequestBody @Valid AdminChangePasswordRequest request
    ) {
        service.forcePasswordReset(id, request);
        return ResponseEntity.ok("Contraseña reseteada exitosamente por el administrador");
    }
}

