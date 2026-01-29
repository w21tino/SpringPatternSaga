# Patrón Saga en Microservicios (Guía práctica con enfoque Spring)

Este documento explica de forma práctica qué es el patrón **Saga**, cómo se usa en microservicios, sus variantes (orquestación/coreografía), ventajas y desventajas, y patrones operativos necesarios (event source/CQRS) . También incluye un ejemplo  y un esquema de implementación típico en **Spring Boot**.

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


---

# Flujo Saga con Axon — SpringPatternSaga

Este proyecto implementa una **Saga de orquestación** con Axon, donde un flujo de negocio se coordina por **Events y Commands** (no por llamadas HTTP entre microservicios).

Módulos implicados (según paquetes):
- ms-order: `OrderAggregate`, `OrderManagementSaga`
- ms-payment: `InvoiceAggregate`
- ms-shipping: `ShippingAggregate`
- core-apis: Commands y Events compartidos (referenciados en imports)

---

## 1) ¿Dónde está la lógica real?

En Axon:
- El **Controller** `OrderCommandController` normalmente solo manda el primer **Command**.
- El **Aggregate** maneja Commands y publica Events.
- La **Saga** reacciona a Events y manda Commands a otros microservicios.
- Los otros microservicios publican Events y la Saga continúa.

En este repo, la orquestación está en:
✅ `com.ms.order.sagas.OrderManagementSaga`

---

## 2) Flujo end-to-end (según tu implementación)

### Paso 1 — Crear orden (ms-order)
**Command:** `CreateOrderCommand`  
**Aggregate:** `OrderAggregate`

Código:
- `OrderAggregate(CreateOrderCommand)` aplica:
  - `OrderCreatedEvent(orderId, itemType, price, currency, orderStatus)`
- `@EventSourcingHandler` de `OrderCreatedEvent` setea el estado del aggregate.

**Evento resultante:**
- `OrderCreatedEvent` (sale al Event Bus)

---

### Paso 2 — Inicia la Saga (ms-order)
**Saga:** `OrderManagementSaga`

Código:
- `@StartSaga`
- `@SagaEventHandler(associationProperty = "orderId")`
- Método: `handle(OrderCreatedEvent orderCreatedEvent)`

Lo que hace:
1) Genera `paymentId` (UUID)
2) Asocia esta instancia de saga con:
   - `orderId` (implícito por el handler con associationProperty="orderId")
   - `paymentId` (explícito):
   - `SagaLifecycle.associateWith("paymentId", paymentId);`

