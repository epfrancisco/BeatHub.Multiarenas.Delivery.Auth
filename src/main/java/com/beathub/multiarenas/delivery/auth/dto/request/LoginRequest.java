package com.beathub.multiarenas.delivery.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "El username o email es obligatorio")
    private String login;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
