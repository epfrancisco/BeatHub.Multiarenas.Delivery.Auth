package com.beathub.multiarenas.delivery.auth.repository;

import com.beathub.multiarenas.delivery.auth.entity.ArenaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArenaUsuarioRepository extends JpaRepository<ArenaUsuario, Long> {

    List<ArenaUsuario> findByUsuarioId(Long usuarioId);

    Optional<ArenaUsuario> findByArenaIdAndUsuarioId(String arenaId, Long usuarioId);
}
