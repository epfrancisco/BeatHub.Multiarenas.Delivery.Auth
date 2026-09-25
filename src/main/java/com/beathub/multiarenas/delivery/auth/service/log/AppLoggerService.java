package com.beathub.multiarenas.delivery.auth.service.log;

import com.beathub.multiarenas.delivery.auth.entity.log.ExceptionLogEntity;
import com.beathub.multiarenas.delivery.auth.entity.log.LogServiciosEntity;
import com.beathub.multiarenas.delivery.auth.repository.log.ExceptionLogRepository;
import com.beathub.multiarenas.delivery.auth.repository.log.LogServiciosRepository;
import com.beathub.multiarenas.delivery.auth.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppLoggerService {

    private final ExceptionLogRepository exceptionLogRepository;
    private final LogServiciosRepository logServiciosRepository;

    private static final Integer MICROSERVICE_AUTH_ID = 1;
    private static final Integer FLUJO_GENERAL_ID = 1;
    private static final Integer SERVICIO_SSO_ID = 2;

    /**
     * Resolves context synchronously before delegating to async DB persist
     */
    public void logException(Throwable ex, String detalle, String flujoId) {
        Long currentUserId = SecurityUtils.getCurrentUsuarioId().orElse(null);
        String currentArenaId = SecurityUtils.getCurrentArenaId().map(String::valueOf).orElse(null);
        logException(ex, detalle, flujoId, currentUserId, currentArenaId);
    }

    @Async
    public void logException(Throwable ex, String detalle, String flujoId, Long usuarioId, String arenaId) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();

            ExceptionLogEntity entity = ExceptionLogEntity.builder()
                    .microservicioId(MICROSERVICE_AUTH_ID)
                    .usuarioId(usuarioId)
                    .arenaId(arenaId)
                    .tipo(ex.getClass().getSimpleName())
                    .mensaje(ex.getMessage() != null ? ex.getMessage() : "Unknown exception")
                    .detalle(detalle)
                    .trace(stackTrace)
                    .flujoId(FLUJO_GENERAL_ID)
                    .creacionFecha(OffsetDateTime.now())
                    .creacionUsuario(usuarioId)
                    .build();

            exceptionLogRepository.save(entity);
        } catch (Exception loggingEx) {
            log.error("Failed to persist exception log into DB: {}", loggingEx.getMessage());
        }
    }

    public void logIntegrationService(
            String arenaId,
            Integer servicioIntegracionId,
            String operacion,
            String requestPayload,
            String responsePayload,
            OffsetDateTime fechaProceso,
            OffsetDateTime fechaRespuesta,
            Long duracionMs,
            Integer httpCode,
            String resultado,
            Integer estadoId) {

        Long currentUserId = SecurityUtils.getCurrentUsuarioId().orElse(null);
        String resolvedArenaId = arenaId != null ? arenaId : SecurityUtils.getCurrentArenaId().map(String::valueOf).orElse(null);

        saveIntegrationServiceAsync(
                resolvedArenaId,
                currentUserId,
                servicioIntegracionId,
                operacion,
                requestPayload,
                responsePayload,
                fechaProceso,
                fechaRespuesta,
                duracionMs,
                httpCode,
                resultado,
                estadoId
        );
    }

    @Async
    public void saveIntegrationServiceAsync(
            String arenaId,
            Long currentUserId,
            Integer servicioIntegracionId,
            String operacion,
            String requestPayload,
            String responsePayload,
            OffsetDateTime fechaProceso,
            OffsetDateTime fechaRespuesta,
            Long duracionMs,
            Integer httpCode,
            String resultado,
            Integer estadoId) {

        try {
            LogServiciosEntity logEntity = LogServiciosEntity.builder()
                    .arenaId(arenaId)
                    .usuarioId(currentUserId)
                    .servicioIntegracionId(servicioIntegracionId != null ? servicioIntegracionId : SERVICIO_SSO_ID)
                    .microservicioId(MICROSERVICE_AUTH_ID)
                    .operacion(operacion)
                    .request(requestPayload)
                    .response(responsePayload)
                    .fechaProceso(fechaProceso != null ? fechaProceso : OffsetDateTime.now())
                    .fechaRespuesta(fechaRespuesta != null ? fechaRespuesta : OffsetDateTime.now())
                    .duracionMs(duracionMs)
                    .httpCode(httpCode)
                    .resultado(resultado)
                    .estadoId(estadoId != null ? estadoId : 1)
                    .creacionFecha(OffsetDateTime.now())
                    .creacionUsuario(currentUserId)
                    .build();

            logServiciosRepository.save(logEntity);
        } catch (Exception loggingEx) {
            log.error("Failed to persist integration service log into DB: {}", loggingEx.getMessage());
        }
    }
}
