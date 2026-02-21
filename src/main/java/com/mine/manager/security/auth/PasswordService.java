package com.mine.manager.security.auth;

import com.mine.manager.exception.EntityNotFoundException;
import com.mine.manager.exception.InvalidValueException;
import com.mine.manager.security.user.ChangePasswordRequest;
import com.mine.manager.security.user.User;
import com.mine.manager.security.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;

        @Transactional
    public void changeOwnPassword(String username, ChangePasswordRequest request) {
        User user = repository.findByUsernameAndStateTrue(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new InvalidValueException("Las nuevas contraseñas no coinciden");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidValueException("La contraseña actual es incorrecta");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidValueException("La nueva contraseña no puede ser igual a la anterior");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}
