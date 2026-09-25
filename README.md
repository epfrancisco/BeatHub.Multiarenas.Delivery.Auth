# Microservicio de Autenticación y Autorización (Auth Service) - BeatHub Multiarenas

Microservicio del ecosistema **BeatHub Multiarenas** desarrollado en **Spring Boot 4.1.1 (Java 25 LTS)** encargado de la autenticación, emisión de tokens JWT, registro de usuarios y personas, roles y scopes.

---

## 🏛️ Estructura del Proyecto

- **Schema de Base de Datos**: `auth`
- **Trazabilidad Homogénea**: `creacion_usuario` (Long), `creacion_fecha`, `actualizacion_usuario` (Long), `actualizacion_fecha`, `estado_id` (Integer), `arena_id`.
- **Flyway**: `src/main/resources/db/migration/V1__init_auth_schema.sql`

---

## 🌐 Endpoints Principales

| Recurso | Método | URL | Descripción |
| :--- | :--- | :--- | :--- |
| **Swagger UI** | `GET` | `http://localhost:8080/api/v1/auth/swagger-ui.html` | Documentación interactiva |
| **Status** | `GET` | `http://localhost:8080/api/v1/auth/status` | Verificación de salud |
| **Login** | `POST` | `http://localhost:8080/api/v1/auth/auth/login` | Autenticación y emisión de JWT |
| **Registro** | `POST` | `http://localhost:8080/api/v1/auth/auth/registro` | Registro de persona y usuario |
| **Validar Token** | `POST` | `http://localhost:8080/api/v1/auth/auth/validate` | Introspección y validación de JWT |
| **Perfil Actual** | `GET` | `http://localhost:8080/api/v1/auth/auth/me` | Obtener datos del usuario autenticado |
| **Consultar Usuario** | `GET` | `http://localhost:8080/api/v1/auth/usuarios/{id}` | Consulta por ID |

---

## 🚀 Despliegue con Docker

```bash
docker compose up --build -d
```
