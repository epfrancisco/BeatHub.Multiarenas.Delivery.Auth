package com.beathub.multiarenas.delivery.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Los datos iniciales y semillas maestras (Roles, Scopes, Arenas y SuperAdmin)
 * son gestionados exclusivamente mediante las migraciones Flyway (db/migration).
 */
@Slf4j
@Configuration
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("BeatHub Auth Service iniciado. Datos semilla gestionados por Flyway.");
    }
}
