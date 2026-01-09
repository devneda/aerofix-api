# AeroFix API 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78C379?style=for-the-badge&logo=mockito&logoColor=white)

API REST desarrollada con Spring Boot para la gestión integral de un taller de mantenimiento de aeronaves.

## 📋 Descripción
Este proyecto implementa una API Web para gestionar aviones, mecánicos, piezas, mantenimientos y registros de vuelo. Ha sido desarrollada siguiendo buenas prácticas de ingeniería de software, incluyendo arquitectura por capas, validaciones, control de excepciones y testing automatizado.

## 🚀 Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 3.x**
* **PostgreSQL** (Persistencia de datos)
* **Spring Data JPA / Hibernate** (ORM)
* **OpenAPI 3.0 (Swagger)** (Documentación interactiva)
* **Mockito** (Tests Unitarios y de Integración)
* **WireMock** (Simulación de API externa)
* **ModelMapper** (Transformación Entidad-DTO)
* **Lombok** (Reducción de código boilerplate)

## ⚙️ Requisitos Previos
Para ejecutar este proyecto necesitas:

1.  **Java JDK 21** instalado.
2.  **PostgreSQL** ejecutándose en el puerto `5432`.
3.  Tener creada una base de datos y un usuario con los siguientes credenciales (o configurarlos en `application.properties`):
    * **Base de datos:** `aerofix_db`
    * **Usuario:** `aerofix_user`
    * **Contraseña:** `aerofix_pass`

## 🛠️ Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone [https://github.com/devneda/aerofix-api.git](https://github.com/devneda/aerofix-api.git)
cd aerofix-api
```

### 2. Ejecutar la aplicación
Puedes usar el wrapper de Maven incluido. Esto descargará todas las dependencias y arrancará el servidor:

```bash
./mvnw spring-boot:run
```

## 📚 Documentación (Swagger UI)

Una vez iniciada la aplicación, la documentación interactiva OpenAPI 3.0 está disponible en:

👉 http://localhost:8080/swagger-ui/index.html

Desde aquí puedes probar todos los endpoints (GET, POST, PUT, DELETE, PATCH).

## ✅ Testing

El proyecto cuenta con una amplia cobertura de tests que aseguran la calidad del código:

* **Unitarios (Service):** Lógica de negocio aislada con Mockito.
* **Integración (Controller):** Pruebas de endpoints HTTP y códigos de estado con `@WebMvcTest`.
* **Mock Externo:** Simulación de conexión a API de proveedor de piezas usando **WireMock**.

Para ejecutar todos los tests:
```bash
./mvnw test
```