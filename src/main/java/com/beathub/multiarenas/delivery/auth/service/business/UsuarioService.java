package com.beathub.multiarenas.delivery.auth.service.business;

import com.beathub.multiarenas.delivery.auth.dto.response.UserProfileResponse;

public interface UsuarioService {

    UserProfileResponse obtenerPorId(Long usuarioId);

    UserProfileResponse obtenerPorUsername(String username);
}
