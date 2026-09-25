-- =============================================================================
-- SCRIPT MANUAL: Creación de Flujos y Estados (Ejecución opcional/manual)
-- =============================================================================

CREATE TABLE IF NOT EXISTS public.flujo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.estado (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    flujo_id INT REFERENCES public.flujo(id)
);

CREATE INDEX IF NOT EXISTS idx_estado_flujo ON public.estado(flujo_id);

-- -----------------------------------------------------------------------------
-- 1. FLUJOS
-- -----------------------------------------------------------------------------
INSERT INTO public.flujo (id, nombre) VALUES
    (1, 'GENERAL'),
    (2, 'SERVICIOS_INTEGRACION')
ON CONFLICT (id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 2. ESTADOS
-- -----------------------------------------------------------------------------

-- Flujo 1: GENERAL (Entidades principales: Usuarios, Roles, Personas, etc.)
INSERT INTO public.estado (id, nombre, flujo_id) VALUES
    (1, 'ACTIVO', 1),
    (2, 'INACTIVO', 1)
ON CONFLICT (id) DO NOTHING;

-- Flujo 2: SERVICIOS_INTEGRACION (Consumo y trazabilidad de servicios externos)
INSERT INTO public.estado (id, nombre, flujo_id) VALUES
    (3, 'EXITOSO', 2),
    (4, 'FALLIDO', 2),
    (5, 'TIMEOUT', 2),
    (6, 'EN_PROCESO', 2),
    (7, 'SIN_RESPUESTA', 2)
ON CONFLICT (id) DO NOTHING;
