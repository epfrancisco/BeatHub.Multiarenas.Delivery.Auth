package com.beathub.multiarenas.delivery.auth.repository;

import com.beathub.multiarenas.delivery.auth.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {

    Optional<Scope> findByNombre(String nombre);
}
