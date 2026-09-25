package com.beathub.multiarenas.delivery.auth.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditableEntity {

    @Column(name = "estado_id", nullable = false)
    @lombok.Builder.Default
    private Integer estadoId = 1;

    @Column(name = "arena_id")
    private Long arenaId;

    @CreatedDate
    @Column(name = "creacion_fecha", nullable = false, updatable = false)
    private LocalDateTime creacionFecha;

    @CreatedBy
    @Column(name = "creacion_usuario", updatable = false)
    private Long creacionUsuario;

    @LastModifiedDate
    @Column(name = "actualizacion_fecha")
    private LocalDateTime actualizacionFecha;

    @LastModifiedBy
    @Column(name = "actualizacion_usuario")
    private Long actualizacionUsuario;
}
