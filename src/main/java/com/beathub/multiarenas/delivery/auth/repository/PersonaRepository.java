package com.beathub.multiarenas.delivery.auth.repository;

import com.beathub.multiarenas.delivery.auth.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByEmail(String email);

    Optional<Persona> findByTipoDocumentoIdAndNumeroDocumento(Integer tipoDocumentoId, String numeroDocumento);

    boolean existsByEmail(String email);

    boolean existsByTipoDocumentoIdAndNumeroDocumento(Integer tipoDocumentoId, String numeroDocumento);
}
