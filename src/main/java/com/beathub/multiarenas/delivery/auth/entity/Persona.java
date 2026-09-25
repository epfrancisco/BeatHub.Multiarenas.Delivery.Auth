package com.beathub.multiarenas.delivery.auth.entity;

import com.beathub.multiarenas.delivery.auth.entity.common.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "persona", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Persona extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "tipo_documento_id", nullable = false)
    private Integer tipoDocumentoId;

    @Column(name = "numero_documento", length = 50, nullable = false)
    private String numeroDocumento;

    @Column(name = "pais_id")
    private Integer paisId;

    @Column(name = "departamento_id")
    private Integer departamentoId;

    @Column(name = "ciudad_id")
    private Integer ciudadId;

    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "telefono", length = 30)
    private String telefono;
}
