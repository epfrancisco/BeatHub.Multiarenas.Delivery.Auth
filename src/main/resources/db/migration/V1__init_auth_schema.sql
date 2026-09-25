CREATE SCHEMA IF NOT EXISTS auth;

-- 1. Tabla Persona
CREATE TABLE IF NOT EXISTS auth.persona (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    tipo_documento_id INT NOT NULL,
    numero_documento VARCHAR(50) NOT NULL,
    pais VARCHAR(50),
    departamento VARCHAR(50),
    ciudad VARCHAR(50),
    email VARCHAR(150) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(30),
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_persona_documento ON auth.persona(tipo_documento_id, numero_documento);
CREATE INDEX IF NOT EXISTS idx_persona_email ON auth.persona(email);
CREATE INDEX IF NOT EXISTS idx_persona_arena ON auth.persona(arena_id);

-- 2. Tabla Usuario
CREATE TABLE IF NOT EXISTS auth.usuario (
    id BIGSERIAL PRIMARY KEY,
    persona_id BIGINT NOT NULL REFERENCES auth.persona(id) ON DELETE CASCADE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tcpos_client_id VARCHAR(100),
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_usuario_username ON auth.usuario(username);
CREATE INDEX IF NOT EXISTS idx_usuario_arena ON auth.usuario(arena_id);

-- 3. Tabla Rol
CREATE TABLE IF NOT EXISTS auth.rol (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_rol_arena ON auth.rol(arena_id);

-- 4. Tabla Scope
CREATE TABLE IF NOT EXISTS auth.scope (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT
);

CREATE INDEX IF NOT EXISTS idx_scope_arena ON auth.scope(arena_id);

-- 5. Tabla Rol Scope
CREATE TABLE IF NOT EXISTS auth.rol_scope (
    id BIGSERIAL PRIMARY KEY,
    rol_id BIGINT NOT NULL REFERENCES auth.rol(id) ON DELETE CASCADE,
    scope_id BIGINT NOT NULL REFERENCES auth.scope(id) ON DELETE CASCADE,
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT,
    UNIQUE(rol_id, scope_id)
);

CREATE INDEX IF NOT EXISTS idx_rol_scope_arena ON auth.rol_scope(arena_id);

-- 6. Tabla Arena Usuario (Multi-tenant)
CREATE TABLE IF NOT EXISTS auth.arena_usuario (
    id BIGSERIAL PRIMARY KEY,
    arena_id VARCHAR(50) NOT NULL,
    usuario_id BIGINT NOT NULL REFERENCES auth.usuario(id) ON DELETE CASCADE,
    estado_id INT NOT NULL DEFAULT 1,
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT
    -- Nota: El constraint unique original
);

CREATE INDEX IF NOT EXISTS idx_arena_usuario_arena ON auth.arena_usuario(arena_id);

-- 7. Tabla Rol Arena Usuario
CREATE TABLE IF NOT EXISTS auth.rol_arena_usuario (
    id BIGSERIAL PRIMARY KEY,
    rol_id BIGINT NOT NULL REFERENCES auth.rol(id) ON DELETE CASCADE,
    arena_usuario_id BIGINT NOT NULL REFERENCES auth.arena_usuario(id) ON DELETE CASCADE,
    estado_id INT NOT NULL DEFAULT 1,
    arena_id VARCHAR(50),
    creacion_fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    creacion_usuario BIGINT,
    actualizacion_fecha TIMESTAMP,
    actualizacion_usuario BIGINT,
    UNIQUE(rol_id, arena_usuario_id)
);

CREATE INDEX IF NOT EXISTS idx_rol_arena_usuario_arena ON auth.rol_arena_usuario(arena_id);

-- Datos Iniciales de Roles
INSERT INTO auth.rol (id, nombre, descripcion, estado_id, arena_id) VALUES
(1, 'SUPER_ADMIN', 'Administrador total de la plataforma', 1, NULL),
(2, 'ADMIN_ARENA', 'Administrador de una arena específica', 1, NULL),
(3, 'RUNNER', 'Repartidor / Validador de entregas', 1, NULL),
(4, 'CLIENTE', 'Usuario final de compras en la App', 1, NULL)
ON CONFLICT (id) DO NOTHING;
