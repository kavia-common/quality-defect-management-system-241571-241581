# Spring Boot Backend (QDMS)

This service provides the REST API for the Quality Defect Management System (QDMS), including JWT authentication and CRUD endpoints for defects and corrective actions.

## Database connectivity (MySQL)

The **database container** provides connection details in:

- `quality-defect-management-system-241571-241583/database/db_connection.txt`

Example content:

```
mysql -u appuser -pdbuser123 -h localhost -P 5000 myapp
```

The backend is configured to read MySQL connection details from environment variables (provided by the platform / container orchestration):

- `MYSQL_URL` (host)
- `MYSQL_PORT` (port)
- `MYSQL_DB` (database name)
- `MYSQL_USER` (username)
- `MYSQL_PASSWORD` (password)

The effective JDBC URL is configured in:

- `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_URL:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:myapp}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:appuser}
spring.datasource.password=${MYSQL_PASSWORD:dbuser123}
```

To ensure the end-to-end preview works, the platform should map the values from `db_connection.txt` to the above env vars (for example: host `localhost`, port `5000`, db `myapp`, user `appuser`, password `dbuser123`).

## CORS (frontend preview)

CORS is enabled and configured via environment variables:

- `ALLOWED_ORIGINS` (comma-separated)
- `ALLOWED_HEADERS` (must include `Authorization`)
- `ALLOWED_METHODS`
- `CORS_MAX_AGE`

This allows the React preview to call the API with `Authorization: Bearer <token>`.

## Key endpoints (high level)

- `POST /api/auth/login` -> returns JWT
- `GET /api/defects`
- `POST /api/defects`
- `GET /api/defects/{id}`
- `PUT /api/defects/{id}`
- `GET /api/defects/{defectId}/actions`
- `POST /api/defects/{defectId}/actions`
- `PUT /api/actions/{actionId}`
- `GET /api/dashboard/severity-distribution`
- `GET /api/dashboard/overdue-actions`

Swagger UI is available at:

- `/docs` (redirects to `/swagger-ui.html`)
- `/api-docs` (OpenAPI JSON)
