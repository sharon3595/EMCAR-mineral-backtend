package com.mine.manager.security.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminChangePasswordRequest {

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres e incluir al menos una mayúscula, una minúscula y un numero."
    )
    private String newPassword;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String confirmationPassword;
}