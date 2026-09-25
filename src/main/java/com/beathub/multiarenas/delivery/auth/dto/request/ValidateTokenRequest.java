package com.beathub.multiarenas.delivery.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidateTokenRequest {

    @NotBlank(message = "El token JWT es obligatorio")
    private String token;
}
