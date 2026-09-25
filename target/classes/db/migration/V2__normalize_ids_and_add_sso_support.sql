-- V2: Normalización de IDs a numéricos y soporte para SSO TuBoleta (Federación y Multi-Arena)

-- 1. Tabla Persona: Convertir campos de texto a IDs numéricos
ALTER TABLE auth.persona DROP COLUMN IF EXISTS pais;
ALTER TABLE auth.persona DROP COLUMN IF EXISTS departamento;
ALTER TABLE auth.persona DROP COLUMN IF EXISTS ciudad;

ALTER TABLE auth.persona ADD COLUMN IF NOT EXISTS pais_id INT;
ALTER TABLE auth.persona ADD COLUMN IF NOT EXISTS departamento_id INT;
ALTER TABLE auth.persona ADD COLUMN IF NOT EXISTS ciudad_id INT;

ALTER TABLE auth.persona ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);

CREATE INDEX IF NOT EXISTS idx_persona_pais ON auth.persona(pais_id);
CREATE INDEX IF NOT EXISTS idx_persona_departamento ON auth.persona(departamento_id);
CREATE INDEX IF NOT EXISTS idx_persona_ciudad ON auth.persona(ciudad_id);

-- 2. Tabla Usuario: Agregar soporte SSO y arena_id numérico
ALTER TABLE auth.usuario ALTER COLUMN password DROP NOT NULL;
ALTER TABLE auth.usuario ADD COLUMN IF NOT EXISTS sso_id VARCHAR(100);
ALTER TABLE auth.usuario ADD COLUMN IF NOT EXISTS sso_provider VARCHAR(50) DEFAULT 'TUBOLETA';
ALTER TABLE auth.usuario ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);

CREATE INDEX IF NOT EXISTS idx_usuario_sso_id ON auth.usuario(sso_id);

-- 3. Tabla Arena Usuario: arena_id numérico y constraint unique
ALTER TABLE auth.arena_usuario ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);
ALTER TABLE auth.arena_usuario DROP CONSTRAINT IF EXISTS arena_usuario_arena_id_usuario_id_key;
ALTER TABLE auth.arena_usuario DROP CONSTRAINT IF EXISTS uq_arena_usuario;
ALTER TABLE auth.arena_usuario ADD CONSTRAINT uq_arena_usuario UNIQUE (arena_id, usuario_id);

-- 4. Tablas Rol, Scope, Rol Scope, Rol Arena Usuario: arena_id numérico
ALTER TABLE auth.rol ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);
ALTER TABLE auth.scope ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);
ALTER TABLE auth.rol_scope ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);
ALTER TABLE auth.rol_arena_usuario ALTER COLUMN arena_id TYPE BIGINT USING (NULLIF(arena_id, '')::BIGINT);
