package com.mine.manager.security.auth;

import com.mine.manager.security.user.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "{auth.name.not-blank}", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 100, message = "{auth.name.size}", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotBlank(message = "{auth.surname.not-blank}", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 100, message = "{auth.surname.size}", groups = {OnCreate.class, OnUpdate.class})
    private String surname;

    @NotBlank(message = "{auth.username.not-blank}", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 4, max = 30, message = "{auth.username.size}", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @NotBlank(message = "{auth.email.not-blank}", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "{auth.email.format}", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotBlank(message = "{auth.password.not-blank}", groups = OnCreate.class)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "{auth.password.pattern}",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String password;

    @NotNull(message = "{auth.role.not-null}", groups = {OnCreate.class, OnUpdate.class})
    private Role role;

    private Boolean state = true;

    public interface OnCreate {}
    public interface OnUpdate {}
}