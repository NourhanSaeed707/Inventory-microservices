# Inventory Microservices

A **Spring Boot microservices-based inventory and booking system** designed using a distributed architecture. The project demonstrates service discovery, centralized configuration, API gateway routing, authentication and authorization, database migration, asynchronous communication, fault tolerance, and containerization.

## Architecture

The system is divided into several independent microservices and infrastructure components:

* **Inventory Service** – Manages events, inventory, available capacity, venues, and ticket prices.
* **Booking Service** – Handles ticket booking requests and communicates with the Inventory Service.
* **Order Service** – Processes booking events and creates orders asynchronously.
* **Gateway** – Provides a single entry point to the microservices and handles routing and security.
* **Discovery Service** – Provides service discovery using Eureka.
* **Config Server** – Centralizes configuration for all microservices.

### Architecture Overview

```text
                         ┌───────────────┐
                         │    Keycloak   │
                         │ Authentication│
                         └───────┬───────┘
                                 │
                                 ▼
                         ┌───────────────┐
                         │    Gateway    │
                         │    :8222      │
                         └───────┬───────┘
                                 │
                  ┌──────────────┼──────────────┐
                  │              │              │
                  ▼              ▼              ▼
          ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
          │   Inventory  │ │   Booking    │ │    Order     │
          │   Service    │ │   Service    │ │   Service    │
          └──────┬───────┘ └──────┬───────┘ └──────┬───────┘
                 │                │                 │
                 │                │                 │
                 ▼                ▼                 ▼
             ┌───────┐        ┌───────┐        ┌───────┐
             │ MySQL │        │ MySQL │        │ MySQL │
             └───────┘        └───────┘        └───────┘

                         ┌───────────────┐
                         │     Kafka     │
                         │ Event Broker  │
                         └───────┬───────┘
                                 │
                                 ▼
                           Order Service

        ┌───────────────────────────────────────────┐
        │              Eureka Discovery             │
        │                    :8761                   │
        └───────────────────────────────────────────┘

        ┌───────────────────────────────────────────┐
        │              Config Server                │
        │                    :8888                   │
        └───────────────────────────────────────────┘
```

## Services

### 1. Inventory Service

Responsible for managing the available inventory for events.

Main responsibilities:

* Create and manage events
* Manage ticket capacity
* Store venue information
* Store ticket prices
* Check available inventory
* Update inventory after bookings

**Port:** `8080`

---

### 2. Booking Service

Responsible for creating ticket bookings.

The Booking Service communicates with the Inventory Service to:

1. Check event availability.
2. Retrieve ticket information.
3. Validate the requested ticket quantity.
4. Calculate the total price.
5. Create a booking.
6. Publish a booking event to Kafka.

**Port:** `8081`

Example booking request:

```json
{
  "userId": 1,
  "eventId": 1,
  "ticketCount": 2
}
```

---

### 3. Order Service

The Order Service processes booking events asynchronously.

When a booking is successfully created, the Booking Service publishes a `BookingEvent` to Kafka.

The Order Service consumes this event and creates an order.

This demonstrates **event-driven communication** between microservices.

**Port:** `8082`

Example flow:

```text
Booking Service
      │
      │ BookingEvent
      ▼
    Kafka
      │
      │ Consume Event
      ▼
 Order Service
      │
      ▼
 Create Order
```

---

### 4. API Gateway

The Gateway provides a single entry point for clients.

Instead of calling every microservice directly, clients communicate through the Gateway.

Example:

```text
Client
  │
  ▼
Gateway :8222
  │
  ├── /api/v1/inventory → Inventory Service
  │
  ├── /api/v1/booking   → Booking Service
  │
  └── /api/v1/order     → Order Service
```

The Gateway is also responsible for:

* Request routing
* Authentication
* Authorization
* Service discovery integration
* Centralized API access

---

### 5. Discovery Service

The project uses **Netflix Eureka** for service discovery.

Each microservice registers itself with Eureka, allowing services to discover and communicate with each other without hardcoding service IP addresses.

**Port:** `8761`

```text
Inventory Service ──┐
Booking Service ────┼──► Eureka Discovery
Order Service ──────┤
Gateway ────────────┘
```

---

### 6. Config Server

The Config Server provides centralized configuration management.

Instead of maintaining configuration separately in every service, configuration can be stored and managed centrally.

**Port:** `8888`

Examples of centralized configuration include:

* Database configuration
* Eureka configuration
* Kafka configuration
* Spring application configuration
* Service-specific settings

---

# Technologies

The project uses the following technologies:

| Technology           | Purpose                          |
| -------------------- | -------------------------------- |
| Java                 | Main programming language        |
| Spring Boot          | Microservice development         |
| Spring Cloud         | Microservices infrastructure     |
| Spring Cloud Gateway | API Gateway                      |
| Netflix Eureka       | Service Discovery                |
| Spring Cloud Config  | Centralized Configuration        |
| Spring Data JPA      | Database access                  |
| Hibernate            | ORM                              |
| MySQL                | Relational database              |
| Flyway               | Database migration               |
| Apache Kafka         | Asynchronous communication       |
| Keycloak             | Authentication & Authorization   |
| Docker               | Containerization                 |
| Docker Compose       | Running multiple services        |
| OpenAPI / Swagger    | API documentation                |
| OpenFeign            | Service-to-service communication |

# Authentication & Authorization

The project uses **Keycloak** to handle authentication and authorization.

Clients authenticate through Keycloak and receive a JWT access token.

The token is then sent with API requests:

```text
Client
   │
   │ Login
   ▼
Keycloak
   │
   │ JWT
   ▼
Client
   │
   │ Authorization: Bearer <token>
   ▼
Gateway
   │
   ▼
Microservice
```

This allows the services to validate authenticated requests and protect APIs.

# Kafka Event-Driven Communication

Apache Kafka is used for asynchronous communication between services.

When a booking is successfully created, the Booking Service publishes a booking event to the Kafka topic:

```text
booking
```

The Order Service listens to this topic:

```text
Booking Service
      │
      │ publish BookingEvent
      ▼
    Kafka
   "booking"
      │
      │ consume
      ▼
 Order Service
```

This approach reduces direct coupling between the Booking and Order services and demonstrates an **event-driven microservices architecture**.

# Database & Flyway Migrations

The services use **MySQL** for persistent data storage.

**Flyway** is used to manage database schema changes through versioned migration scripts.

Example:

```text
src/main/resources/db/migration/

V1__create_events_table.sql
V2__create_bookings_table.sql
V3__create_orders_table.sql
```

Instead of manually modifying the database schema, Flyway automatically applies pending migrations when the application starts.

This provides:

* Version-controlled database changes
* Consistent database environments
* Repeatable deployments
* Easier database management between development environments

# Docker & Docker Compose

The project uses Docker to containerize the infrastructure and services.

Docker Compose is used to run the complete system together.

The environment can include:

```text
┌───────────────────────────────────────┐
│           Docker Compose              │
│                                       │
│  Gateway                              │
│  Inventory Service                    │
│  Booking Service                      │
│  Order Service                        │
│  Config Server                        │
│  Eureka Discovery                     │
│  Keycloak                             │
│  MySQL                                │
│  Kafka                                │
│                                       │
└───────────────────────────────────────┘
```

This makes it easier to start the complete microservices environment without manually running every infrastructure component.

# API Documentation

The project uses **Swagger / OpenAPI** to document and test the REST APIs.

## Booking Service Swagger

![Booking Service Swagger](./screenshots/booking-swagger.png)

## Inventory Service Swagger

![Inventory Service Swagger](./screenshots/inventory-swagger.png)

The Swagger UI provides an interactive interface for:

* Viewing available endpoints
* Reviewing request and response models
* Testing APIs
* Understanding API parameters
* Testing authenticated endpoints

# Project Structure

```text
inventory-microservices/
│
├── config-server/
│
├── discovery/
│
├── gateway/
│
├── inventory-service/
│
├── booking-service/
│
├── order-service/
│
├── screenshots/
│   ├── booking-swagger.png
│   └── inventory-swagger.png
│
├── docker-compose.yml
│
└── README.md
```

# How to Run

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd inventory-microservices
```

### 2. Start the infrastructure and services

Using Docker Compose:

```bash
docker compose up -d
```

### 3. Verify the services

The main components are available on:

| Component         |   Port |
| ----------------- | -----: |
| Gateway           | `8222` |
| Inventory Service | `8080` |
| Booking Service   | `8081` |
| Order Service     | `8082` |
| Eureka Discovery  | `8761` |
| Config Server     | `8888` |
| Keycloak          | `8091` |

### 4. Access the APIs

The recommended approach is to access the services through the API Gateway:

```text
http://localhost:8222
```

# Key Features

* Microservices architecture
* Service discovery with Eureka
* Centralized configuration with Spring Cloud Config
* API Gateway
* JWT-based authentication with Keycloak
* Role-based authorization
* Event-driven communication using Kafka
* Synchronous service communication using OpenFeign
* MySQL persistence
* Database versioning with Flyway
* REST APIs
* OpenAPI / Swagger documentation
* Docker containerization
* Docker Compose orchestration
* Independent service deployment
* Separation of responsibilities between services

# Learning Objectives

This project was built to demonstrate practical experience with:

* Designing Spring Boot microservices
* Communication between distributed services
* Service discovery
* API Gateway patterns
* Authentication and authorization
* Event-driven architecture
* Kafka producers and consumers
* Database migrations
* Containerized development
* REST API design
* Distributed system configuration

# Author

**Nourhan Saeed**

Backend-focused Full-Stack Developer | Java & Spring Boot
