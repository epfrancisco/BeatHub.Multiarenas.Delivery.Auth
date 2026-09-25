package com.beathub.multiarenas.delivery.auth.service.business.impl;

import com.beathub.multiarenas.delivery.auth.dto.response.UserProfileResponse;
import com.beathub.multiarenas.delivery.auth.entity.Usuario;
import com.beathub.multiarenas.delivery.auth.exception.ResourceNotFoundException;
import com.beathub.multiarenas.delivery.auth.repository.UsuarioRepository;
import com.beathub.multiarenas.delivery.auth.service.business.AuthService;
import com.beathub.multiarenas.delivery.auth.service.business.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse obtenerPorId(Long usuarioId) {
        return authService.obtenerPerfil(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse obtenerPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        return authService.obtenerPerfil(usuario.getId());
    }
}
