# Distributed DB Router: Spring Boot Read-Write Splitting

### 🚀 Overview

In high-traffic distributed systems, the database often becomes the primary bottleneck. This project demonstrates a production-grade implementation of **Read-Write Splitting** in a Spring Boot environment.

By routing all **Write** operations to a Primary node and **Read** operations to Replica nodes, we achieve horizontal scalability and high availability, significantly reducing the load on the primary database instance.

---

### 🛠 Architecture & Features

* **Dynamic Data Routing**: Uses `AbstractRoutingDataSource` to switch database connections at runtime based on the execution context.
* **AOP-Driven Switching**: Custom `@ReadOnlyConnection` annotation and Aspect-Oriented Programming (AOP) to make routing transparent to the business logic.
* **Context Management**: Thread-safe switching using `ThreadLocal` to ensure no cross-request data leaks.
* **Failover Ready**: Configured with a default fallback to the Primary node if no routing key is specified.

---

### 📂 Project Structure

```text
src/main/java/com/example/db/
├── annotation/      # Custom @ReadOnlyConnection annotation
├── aspect/          # AOP logic to intercept methods and set Routing Key
├── config/          # DataSource configuration and Routing logic
├── controller/      # REST Endpoints for Order management
├── entity/          # JPA Entities
├── repository/      # Spring Data JPA Repositories
└── service/         # Business logic with routing annotations

```

---

### ⚙️ Getting Started

#### 1. Prerequisites

* Docker & Docker Compose
* JDK 17+
* Maven 3.6+

#### 2. Spin up the Infrastructure

Use the provided `docker-compose.yml` to start two independent MySQL instances:

* **Primary Node**: Port `3307`
* **Replica Node**: Port `3308`

```bash
docker-compose up -d

```

#### 3. Run the Application

```bash
mvn spring-boot:run

```

---

### 🧪 Verifying the Logic

#### Step 1: Create an Order (Write)

The system defaults to the **Primary** node for all non-annotated methods.

```bash
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{"productName": "MacBook Pro", "price": 2500}'

```

#### Step 2: Fetch Orders (Read)

The `@ReadOnlyConnection` annotation triggers the AOP aspect, routing the query to the **Replica** node.

```bash
curl -X GET http://localhost:8080/orders

```

---

### 💡 Key Technical Insights

1. **The Join Tax & Scalability**: By moving read-heavy queries to replicas, we free up the Primary's CPU for critical ACID-compliant transactions (e.g., payments/orders).
2. **Eventual Consistency**: This architecture assumes a small replication lag. In a real-world scenario, the Replica nodes stay synchronized via MySQL's Binary Logs (binlogs).
3. **AOP Order**: The `DataSourceAspect` is configured with `@Order(0)` to ensure the routing key is set *before* the transaction starts.

---

### 👤 Author

**Akash Giri** *Senior Java Backend Developer | Distributed Systems Specialist*

---
