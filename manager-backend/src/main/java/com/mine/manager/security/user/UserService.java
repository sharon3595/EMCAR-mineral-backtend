package com.mine.manager.security.user;

import com.mine.manager.exception.DuplicateException;
import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.security.auth.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponseDto findById(Integer id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public UserResponseDto update(Integer id, RegisterRequestDto request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (repository.existsByUsernameAndIdNot(request.getUsername(), id)) {
            throw new DuplicateException("Usuario", "nombre", request.getUsername());
        }

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setState(request.getState());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        repository.save(user);
        return mapToResponse(user);
    }

    public void delete(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        repository.delete(user);
    }

    private UserResponseDto mapToResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .state(user.getState())
                .build();
    }
}
