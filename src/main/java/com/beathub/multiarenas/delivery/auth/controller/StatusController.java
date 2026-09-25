package com.beathub.multiarenas.delivery.auth.controller;

import com.beathub.multiarenas.delivery.auth.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/status")
@Tag(name = "Status", description = "Verificación de estado y salud del microservicio")
public class StatusController {

    @GetMapping
    @Operation(summary = "Verificar estado del microservicio")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatus() {
        Map<String, Object> status = Map.of(
                "service", "beathub-auth",
                "version", "1.0.0",
                "status", "UP",
                "timestamp", LocalDateTime.now()
        );
        return ResponseEntity.ok(ApiResponse.success("Microservicio Auth operativo", status));
    }
}
