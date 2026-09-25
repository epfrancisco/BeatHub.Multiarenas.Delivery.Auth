-- V4: Actualizar hash BCrypt exacto para password 'Admin12345!' del SuperAdmin
UPDATE auth.usuario 
SET password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi'
WHERE username = 'superadmin' OR id = 1;
