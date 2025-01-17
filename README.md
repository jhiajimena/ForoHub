# 💥Challenge Foro Hub 

Foro Hub es un backend diseñado para administrar 
un foro dinámico, ofreciendo herramientas 
para que los usuarios puedan:
- Crear
- Explorar 
- Moderar temas organizados por cursos, categorías y subcategorías. 


## 📌Funcionalidades Principales
. Gestión de usuarios y roles: Control total sobre permisos y accesos (solo administradores y desarrolladores).
. Autenticación segura: Implementación de JWT para la seguridad en los endpoints.
. API RESTful: Desarrollada con Spring Boot para una experiencia robusta y escalable.
. CRUD completo: Crear, leer, actualizar y eliminar temas, respuestas, categorías y subcategorías.
. Configuración automática: Inicialización de roles y usuarios predeterminados al iniciar.
. Migración de base de datos: Uso de Flyway para la gestión de migraciones de la base de datos.


## 📌Instalación

. Java 17 o superior.
. Maven configurado.
. PostgreSQL activo.

### 📌Guía de Instalación
.Configura la base de datos:
   1. Crea una base de datos PostgreSQL
   2. Conecta el proyecto a través de variables de entorno o modificando application.properties.

. Clonar el repositorio:
   ```bash git clone 
   https://github.com/jhiajimena/ForoHub.git
   ```
. Configurar el proyecto:
Actualiza las variables de entorno o el archivo
`application.properties`:
   ```properties
   spring.datasource.url= jdbc:postgresql://{DB_HOST}/{DB_NAME_FORO}
   spring.datasource.username={DB_USER}
   spring.datasource.password={DB_PASSWORD}
   security.jwt.key.private=${PRIVATE_KEY}
   security.jwt.user.generator=${USER_GENERATOR}
   security.authjwt.backend=${YOUR AUTHORIZATHION}
   ```
. Ejecutar el proyecto:
   ```bash
   ./mvnw spring-boot:run
   ```
. Accede a la aplicación en:

`http://localhost:8080`

## 📌Datos Inicializados Automáticamente
Al iniciar, el sistema genera:

Permisos: 
CREATE_TOPIC | 
DELETE_USER | 
entre otros.

Roles predefinidos: 
DEVELOPER| 
ADMIN| 
MODERATOR| 
USER
- **Usuarios predeterminados**:
    - **Email**: `root@gmail.com` | **Contraseña**: `root1234`
    - **Email**: `admin@gmail.com` | **Contraseña**: `admin1234`

## 📌Endpoints Principales
| Método | Endpoint                   | Descripción                              | Parámetros / Body       |
|--------|----------------------------|------------------------------------------|-------------------------|
| GET    | `/api/topics`              | Obtener todos los temas                  | -                       |
| GET    | `/api/courses/{id}/topics` | Obtener temas de un curso específico     | `{id}`: ID del curso    |
| POST   | `/api/topics`              | Crear un nuevo tema                      | `{title, content, ...}` |
| POST   | `/api/auth/signUp`         | Crear un nuevo usuario                   | `{email, password, ...}`|
| POST   | `/api/auth/login`          | Autenticar un usuario                    | `{email, password}`     |

## 📌Seguridad
El backend implementa autenticación y autorización con:
- **Spring Security** ➡️Para la app en general y la autenticación.
- **JWT Tokens** ➡️ Para proteger los endpoints.
- **BCrypt** ➡️ Para el cifrado de contraseñas.

## 📌Estructura del Proyecto
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── lescano/forohub/
│   │   │   ├── config/         # Configuración general
│   │   │   ├── controllers/    # Controladores REST
│   │   │   ├── entities/       # Entidades de la base de datos
│   │   │   ├── repository/     # Repositorios de JPA
│   │   │   └── services/       # Lógica de negocio
│   └── resources/
│       ├── application.properties
```
## 📌Tecnologías Usadas
### Backend
- **Java 17**        ➡️ Lenguaje principal.
- **Spring Boot 3**  ➡️Framework para desarrollo rápido.
- **Spring Security**➡️ Manejo de autenticación y autorización.
- **Spring Data JPA**➡️ Persistencia de datos.
- **Spring Validation**➡️ Validaciones.
- **Spring test**➡️ Pruebas unitarias y pruebas de integración.

### Bases de Datos
- **PostgreSQL**➡️ Base de datos relacional.
- **Flyway**    ➡️Migración y versionamiento de bases de datos.

### Herramientas y Utilidades
- **Lombok** ➡️ Simplificación del código Java.
- **Maven**  ➡️ Gestión de dependencias.
- **SonarQube**➡️ Análisis de la calidad del código.

### Pamela Lescano

![linkedin](assets/linkedin.png)
Linkedin: www.linkedin.com/in/pamela-lescano-vila

## **Agradecimiento:**

![alura](assets/logo-aluraespanhol.png)     ![one-education](assets/one.png)
