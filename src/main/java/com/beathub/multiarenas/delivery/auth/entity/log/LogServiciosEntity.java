package com.beathub.multiarenas.delivery.auth.entity.log;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_servicios", schema = "public")
public class LogServiciosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arena_id", length = 50)
    private String arenaId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "servicio_integracion_id", nullable = false)
    private Integer servicioIntegracionId;

    @Column(name = "microservicio_id", nullable = false)
    private Integer microservicioId;

    @Column(name = "operacion", nullable = false, length = 150)
    private String operacion;

    @Column(name = "request", columnDefinition = "TEXT")
    private String request;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "fecha_proceso", nullable = false)
    @Builder.Default
    private OffsetDateTime fechaProceso = OffsetDateTime.now();

    @Column(name = "fecha_respuesta")
    private OffsetDateTime fechaRespuesta;

    @Column(name = "duracion_ms")
    private Long duracionMs;

    @Column(name = "http_code")
    private Integer httpCode;

    @Column(name = "resultado", length = 50)
    private String resultado;

    @Column(name = "estado_id", nullable = false)
    @Builder.Default
    private Integer estadoId = 1;

    @Column(name = "creacion_fecha", nullable = false)
    @Builder.Default
    private OffsetDateTime creacionFecha = OffsetDateTime.now();

    @Column(name = "creacion_usuario")
    private Long creacionUsuario;
}
