# Mine Manager (Monolito)
Mine Manager es una plataforma centralizada diseñada para la gestión integral de procesos mineros, incluyendo proveedores, lotes, cargas y administración de usuarios, operando bajo una arquitectura monolítica robusta.

# Integrantes del proyecto
Los siguientes integrantes son:

**Dev 1** - [Nombre] [TABLERO-Tareas](link-al-tablero)

**Dev 2** - [Nombre] [TABLERO-Tareas](link-al-tablero)

# Documentación
La documentación técnica de los endpoints (API) se encuentra disponible vía Swagger una vez iniciada la aplicación:

* **Swagger local UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **Swagger prod UI:** [https://emcar-mineral-backtend.onrender.com/swagger-ui/index.html](https://emcar-mineral-backtend.onrender.com/swagger-ui/index.html)
* emcar-mineral-backtend-production.up.railway.app
* **JSON Docs:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Prerrequisitos
* Java 17+
* PostgreSQL 14+
* Gradle

## Configuración Base de Datos
El sistema utiliza el esquema **`mine`** para aislar las tablas del negocio. No es necesario crear el esquema manualmente; el sistema lo generará al arrancar.

1. Crear una base de datos vacía en PostgreSQL (`minemanager_db`).

## Configuración entorno local
**Servidor API entorno local:** http://localhost:8080/api/v1

**Datasource:**
```properties
jdbc:postgresql://localhost:5432/minemanager_db?currentSchema=mine

