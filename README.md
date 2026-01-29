# Patrón Saga en Microservicios (Guía práctica con enfoque Spring)

Este documento explica de forma práctica qué es el patrón **Saga**, cómo se usa en microservicios, sus variantes (orquestación/coreografía), ventajas y desventajas, y patrones operativos necesarios (Outbox, idempotencia, DLQ, métricas). También incluye un ejemplo de caso real y un esquema de implementación típico en **Spring Boot**.

---

## 1) ¿Qué es el patrón Saga?

El patrón **Saga** es una estrategia para manejar **transacciones distribuidas** (operaciones que abarcan varios microservicios) **sin usar** una transacción ACID global (por ejemplo, 2PC/XA).

Una Saga divide una operación grande (p. ej. “crear una orden”) en una secuencia de **transacciones locales** (cada microservicio actualiza solo su propia base de datos).  
Si un paso falla, se ejecutan **acciones compensatorias** para “deshacer” lógicamente lo ya realizado.

**Idea clave:** en una Saga se busca **consistencia eventual**, no consistencia inmediata.

---

## 2) ¿Por qué se usa Saga en vez de 2PC/XA?

### ¿Qué es 2PC/XA?
2PC/XA intenta que varios servicios/BD confirmen una “misma transacción” como si fuera una sola, coordinada por un “coordinador
