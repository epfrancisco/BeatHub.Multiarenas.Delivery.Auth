-- =============================================================================
-- ESQUEMA COMPARTIDO (PUBLIC): Tablas de Microservicios, Integraciones
-- y Registro Centralizado de Logs & Excepciones
-- =============================================================================

-- 1. Catálogo de Microservicios
CREATE TABLE IF NOT EXISTS public.microservicio (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    estado_id INT NOT NULL DEFAULT 1
);

-- 2. Catálogo de Servicios de Integración Externos (TCPOS, Credibanco, etc.)
CREATE TABLE IF NOT EXISTS public.servicio_integracion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    estado_id INT NOT NULL DEFAULT 1
);

-- 3. Tabla Exception Log (Excepciones no controladas y errores de sistema)
CREATE TABLE IF NOT EXISTS public.exception_log (
    id BIGSERIAL PRIMARY KEY,
    microservicio_id INT REFERENCES public.microservicio(id),
    usuario_id BIGINT,
    arena_id VARCHAR(50),
    tipo VARCHAR(150) NOT NULL,
    mensaje TEXT NOT NULL,
    detalle TEXT,
    trace TEXT,
    flujo_id INT DEFAULT 1,
    creacion_fecha TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_exception_log_fecha ON public.exception_log(creacion_fecha DESC);
CREATE INDEX IF NOT EXISTS idx_exception_log_ms ON public.exception_log(microservicio_id);
CREATE INDEX IF NOT EXISTS idx_exception_log_usuario ON public.exception_log(usuario_id);
CREATE INDEX IF NOT EXISTS idx_exception_log_arena ON public.exception_log(arena_id);
CREATE INDEX IF NOT EXISTS idx_exception_log_flujo ON public.exception_log(flujo_id);

-- 4. Tabla Log de Servicios (Trazabilidad de peticiones a servicios externos)
CREATE TABLE IF NOT EXISTS public.log_servicios (
    id BIGSERIAL PRIMARY KEY,
    arena_id VARCHAR(50),
    usuario_id BIGINT,
    servicio_integracion_id INT NOT NULL REFERENCES public.servicio_integracion(id),
    microservicio_id INT NOT NULL REFERENCES public.microservicio(id),
    operacion VARCHAR(150) NOT NULL,
    request TEXT,
    response TEXT,
    fecha_proceso TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta TIMESTAMPTZ,
    duracion_ms BIGINT,
    http_code INT,
    resultado VARCHAR(50),
    estado_id INT NOT NULL DEFAULT 1,
    creacion_fecha TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_log_servicios_fecha ON public.log_servicios(creacion_fecha DESC);
CREATE INDEX IF NOT EXISTS idx_log_servicios_arena ON public.log_servicios(arena_id);
CREATE INDEX IF NOT EXISTS idx_log_servicios_usuario ON public.log_servicios(usuario_id);
CREATE INDEX IF NOT EXISTS idx_log_servicios_integracion ON public.log_servicios(servicio_integracion_id);
CREATE INDEX IF NOT EXISTS idx_log_servicios_ms ON public.log_servicios(microservicio_id);

-- =============================================================================
-- SEED DATA INICIAL
-- =============================================================================

-- Seed Microservicios
INSERT INTO public.microservicio (id, nombre, estado_id) VALUES
    (1, 'AUTH', 1),
    (2, 'CATALOG', 1),
    (3, 'ORDERING', 1),
    (4, 'PAYMENT', 1),
    (5, 'NOTIFICATION', 1),
    (6, 'EVENT', 1)
ON CONFLICT (id) DO NOTHING;

-- Seed Servicios de Integración
INSERT INTO public.servicio_integracion (id, nombre, estado_id) VALUES
    (1, 'TCPOS', 1),
    (2, 'CREDIBANCO', 1),
    (3, 'ADS_SSO', 1)
ON CONFLICT (id) DO NOTHING;
