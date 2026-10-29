# Monument repository and HTTP exercise

[Español](README.es.md) · [More work](https://github.com/navalcode)

## Overview

A coursework Spring MVC/JPA example under `EO2/`. Its `/monumento` routes demonstrate create, list, read, update and delete with the original model and repository. The review makes absent resources return 404 and rejects an identifier supplied to POST, preventing a create request from overwriting an existing row.

## Run the contracts

Requires JDK 17. From the repository root, in a Unix shell:

```sh
cd EO2
sh ./mvnw verify
```

The seven local checks comprise the original context smoke test and six HTTP journeys with the actual Spring context and H2. They cover generated IDs, JSON fields/list/read, valid updates, missing reads/updates, delete 204 then 404, and refusing an overwrite through POST. Failing requests leave the stored state unchanged.

| Route | Confirmed result |
|---|---|
| `GET /monumento` |200 and the collection |
| `GET /monumento/{id}` |200 or 404 |
| `POST /monumento` |201 with a generated ID;400 if an ID is supplied |
| `PUT /monumento/{id}` |200 retaining the ID, or 404 |
| `DELETE /monumento/{id}` |204 for an existing row,404 when absent |

Read `MonumentHttpJourneyTest`, then `MonumentoController` and the model. Tests use fictional monuments, memory-only H2 and a non-fetched example image URL. The original bootstrap seed remains in the exercise. Fields and existing successful response shapes are preserved.

## Validation

JDK 17/macOS passed all seven checks and Maven `verify` on 29 October 2026. HTTP coverage uses MockMvc/JPA, not a deployed service; [results and scope](docs/verification.json). The original Maven 3.8.2/Spring Boot 2.5.5 pins and attribution/history remain. Hosted CI was not added. [Contract presentation decisions](DESIGN.md).

## Resumen

La versión española completa explica los mismos contratos, comandos y límites: [README.es.md](README.es.md).
