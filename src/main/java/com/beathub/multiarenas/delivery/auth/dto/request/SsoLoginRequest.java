package com.beathub.multiarenas.delivery.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SsoLoginRequest {

    @NotBlank(message = "El identificador de SSO (ssoId) es obligatorio")
    private String ssoId;

    private String ssoProvider;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato de email es inválido")
    private String email;

    private ClientType clientType = ClientType.USER_APP;

    private Long arenaId;

    private String nombres;

    private String apellidos;

    private Integer tipoDocumentoId;

    private String numeroDocumento;

    private String telefono;

    private Integer paisId;

    private Integer departamentoId;

    private Integer ciudadId;

    private String direccion;

    private String ssoToken;
}
