-- V3: Seed de Usuario SuperAdmin Base y configuración multi-arena inicial
-- Arena 1: DaviArena
-- Arena 2: MovistarArena

-- 1. Persona Base SuperAdmin
INSERT INTO auth.persona (
    id, nombres, apellidos, tipo_documento_id, numero_documento, 
    email, telefono, pais_id, departamento_id, ciudad_id, estado_id, creacion_fecha
) VALUES (
    1, 'Super', 'Administrador', 1, '0000000000', 
    'superadmin@beathub.com', '+573000000000', 1, 11, 11001, 1, NOW()
) ON CONFLICT (id) DO NOTHING;

-- 2. Usuario SuperAdmin (Compatible con Login Local y Login SSO TuBoleta)
-- Password encriptado BCrypt para 'Admin12345!': $2a$10$eAccYoNOz2VLPUgPO10B.OPL5X4bAev76q121q72g78G68yG3eJ.O
INSERT INTO auth.usuario (
    id, persona_id, username, password, sso_id, sso_provider, estado_id, arena_id, creacion_fecha
) VALUES (
    1, 1, 'superadmin', '$2a$10$eAccYoNOz2VLPUgPO10B.OPL5X4bAev76q121q72g78G68yG3eJ.O', 
    'TBL-SUPERADMIN-001', 'TUBOLETA', 1, 1, NOW()
) ON CONFLICT (id) DO NOTHING;

-- 3. Vinculación del SuperAdmin a las Arenas Iniciales
-- Arena 1: DaviArena
INSERT INTO auth.arena_usuario (id, arena_id, usuario_id, estado_id, creacion_fecha)
VALUES (1, 1, 1, 1, NOW())
ON CONFLICT (id) DO NOTHING;

-- Arena 2: MovistarArena
INSERT INTO auth.arena_usuario (id, arena_id, usuario_id, estado_id, creacion_fecha)
VALUES (2, 2, 1, 1, NOW())
ON CONFLICT (id) DO NOTHING;

-- 4. Asignación de Rol SUPER_ADMIN (Rol ID = 1) en ambas arenas
INSERT INTO auth.rol_arena_usuario (id, rol_id, arena_usuario_id, estado_id, arena_id, creacion_fecha)
VALUES (1, 1, 1, 1, 1, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO auth.rol_arena_usuario (id, rol_id, arena_usuario_id, estado_id, arena_id, creacion_fecha)
VALUES (2, 1, 2, 1, 2, NOW())
ON CONFLICT (id) DO NOTHING;

-- 5. Sincronizar secuencias de PostgreSQL para futuros inserts autoincrementales
SELECT setval('auth.persona_id_seq', COALESCE((SELECT MAX(id) FROM auth.persona), 1));
SELECT setval('auth.usuario_id_seq', COALESCE((SELECT MAX(id) FROM auth.usuario), 1));
SELECT setval('auth.arena_usuario_id_seq', COALESCE((SELECT MAX(id) FROM auth.arena_usuario), 1));
SELECT setval('auth.rol_arena_usuario_id_seq', COALESCE((SELECT MAX(id) FROM auth.rol_arena_usuario), 1));
