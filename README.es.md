# Monument repository and HTTP exercise

[English](README.md) · [Más proyectos](https://github.com/navalcode)

## Resumen

Ejemplo formativo Spring MVC/JPA dentro de `EO2/`. Las rutas `/monumento` enseñan creación, listado, consulta, edición y borrado con el modelo y repositorio originales. La revisión devuelve 404 ante recursos ausentes y rechaza IDs enviados a POST, evitando sobrescribir una fila mediante una creación.

## Ejecutar los contratos

Requiere JDK 17. Desde la raíz, en una shell Unix:

```sh
cd EO2
sh ./mvnw verify
```

Las siete comprobaciones locales incluyen el arranque original y seis recorridos HTTP con contexto Spring real y H2. Cubren IDs generados, campos JSON/listado/consulta, edición válida, consultas/ediciones ausentes, borrado 204 seguido de 404 y rechazo de sobrescritura por POST. Las peticiones fallidas conservan el estado almacenado.

| Ruta | Resultado comprobado |
|---|---|
| `GET /monumento` |200 y colección |
| `GET /monumento/{id}` |200 o 404 |
| `POST /monumento` |201 con ID generado;400 si se aporta un ID |
| `PUT /monumento/{id}` |200 conservando el ID, o 404 |
| `DELETE /monumento/{id}` |204 si existe,404 si está ausente |

Lee `MonumentHttpJourneyTest`, después `MonumentoController` y el modelo. Los tests usan monumentos ficticios, H2 en memoria y una URL de imagen que no se descarga. Se conserva el seed original del ejercicio y las formas existentes de las respuestas de éxito.

## Validación

JDK 17/macOS pasa las siete comprobaciones y Maven `verify` el 29 de octubre de 2026. El recorrido HTTP usa MockMvc/JPA; no acredita un servicio desplegado. [Resultados y alcance](docs/verification.json). Se conservan Maven 3.8.2/Spring Boot 2.5.5, atribución e historial; esta revisión no añade CI alojada. [Decisiones de presentación del contrato](DESIGN.md).
