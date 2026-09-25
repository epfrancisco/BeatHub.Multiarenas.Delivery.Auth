package com.beathub.multiarenas.delivery.auth.security;

import com.beathub.multiarenas.delivery.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecretKey("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        jwtProperties.setExpirationMs(3600000L); // 1 hora
        jwtProperties.setIssuer("beathub-auth-service");

        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    void generarYValidarToken_Exitoso() {
        Long usuarioId = 100L;
        String username = "juan.perez";
        String email = "juan@example.com";
        String ssoId = "TBL-CONTACT-999";
        Long personaId = 50L;
        List<String> roles = List.of("ROLE_CLIENTE");
        List<String> scopes = List.of("api:access");
        Long currentArenaId = 1L;
        List<Long> arenaIds = List.of(1L, 2L);

        String token = jwtTokenProvider.generarToken(usuarioId, username, email, ssoId, personaId, roles, scopes, currentArenaId, arenaIds);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validarToken(token));

        Claims claims = jwtTokenProvider.extraerClaims(token);
        assertEquals(String.valueOf(usuarioId), claims.getSubject());
        assertEquals(username, claims.get("username", String.class));
        assertEquals(email, claims.get("email", String.class));
        assertEquals(ssoId, claims.get("ssoId", String.class));
        assertEquals(personaId, claims.get("personaId", Long.class));
        assertEquals(currentArenaId, claims.get("currentArenaId", Long.class));
        assertEquals(usuarioId, jwtTokenProvider.extraerUsuarioId(token));

        UserPrincipal principal = jwtTokenProvider.extraerUserPrincipal(token);
        assertNotNull(principal);
        assertEquals(usuarioId, principal.getUsuarioId());
        assertEquals(username, principal.getUsername());
        assertEquals(email, principal.getEmail());
        assertEquals(ssoId, principal.getSsoId());
        assertEquals(personaId, principal.getPersonaId());
        assertEquals(currentArenaId, principal.getCurrentArenaId());
        assertEquals(2, principal.getArenaIds().size());
        assertEquals(1, principal.getAuthorities().size());
        assertTrue(principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE")));
    }

    @Test
    void validarToken_TokenInvalido_RetornaFalse() {
        assertFalse(jwtTokenProvider.validarToken("token_invalido"));
        assertFalse(jwtTokenProvider.validarToken(""));
        assertFalse(jwtTokenProvider.validarToken(null));
    }
}
