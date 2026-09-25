package com.beathub.multiarenas.delivery.auth.controller;

import com.beathub.multiarenas.delivery.auth.dto.request.LoginRequest;
import com.beathub.multiarenas.delivery.auth.dto.request.RegistroRequest;
import com.beathub.multiarenas.delivery.auth.dto.request.SsoLoginRequest;
import com.beathub.multiarenas.delivery.auth.dto.request.ValidateTokenRequest;
import com.beathub.multiarenas.delivery.auth.dto.response.ApiResponse;
import com.beathub.multiarenas.delivery.auth.dto.response.AuthResponse;
import com.beathub.multiarenas.delivery.auth.dto.response.TokenValidationResponse;
import com.beathub.multiarenas.delivery.auth.dto.response.UserProfileResponse;
import com.beathub.multiarenas.delivery.auth.exception.UnauthorizedException;
import com.beathub.multiarenas.delivery.auth.security.SecurityUtils;
import com.beathub.multiarenas.delivery.auth.security.UserPrincipal;
import com.beathub.multiarenas.delivery.auth.service.business.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints de Login, Registro, SSO TuBoleta, Validación y Renovación de Tokens JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión tradicional y obtener Token JWT")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso", response));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario y persona tradicional")
    public ResponseEntity<ApiResponse<AuthResponse>> registro(@Valid @RequestBody RegistroRequest request) {
        AuthResponse response = authService.registro(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado exitosamente", response));
    }

    @PostMapping("/sso/login")
    @Operation(summary = "Iniciar sesión o registrarse mediante SSO de TuBoleta (USER_APP, RUNNER_APP, ADMIN_WEB)")
    public ResponseEntity<ApiResponse<AuthResponse>> ssoLogin(@Valid @RequestBody SsoLoginRequest request) {
        AuthResponse response = authService.ssoLogin(request);
        return ResponseEntity.ok(ApiResponse.success("Autenticación SSO exitosa", response));
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token JWT e introspección de claims (Gateway / Servicios)")
    public ResponseEntity<ApiResponse<TokenValidationResponse>> validate(@Valid @RequestBody ValidateTokenRequest request) {
        TokenValidationResponse response = authService.validarToken(request);
        return ResponseEntity.ok(ApiResponse.success("Validación completada", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener perfil del usuario autenticado", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<UserProfileResponse>> me(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestHeader(value = "X-User-Id", required = false) Long headerUserId) {
        Long usuarioId = resolveUserId(principal, headerUserId);
        UserProfileResponse response = authService.obtenerPerfil(usuarioId);
        return ResponseEntity.ok(ApiResponse.success("Perfil de usuario recuperado", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar Token JWT o cambiar contexto de arena activa", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestHeader(value = "X-User-Id", required = false) Long headerUserId,
            @RequestParam(value = "arenaId", required = false) Long arenaId) {
        Long usuarioId = resolveUserId(principal, headerUserId);
        AuthResponse response = authService.refreshToken(usuarioId, arenaId);
        return ResponseEntity.ok(ApiResponse.success("Token renovado exitosamente", response));
    }

    private Long resolveUserId(UserPrincipal principal, Long headerUserId) {
        if (principal != null && principal.getUsuarioId() != null) {
            return principal.getUsuarioId();
        }
        if (headerUserId != null) {
            return headerUserId;
        }
        return SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new UnauthorizedException("Usuario no autenticado"));
    }
}
