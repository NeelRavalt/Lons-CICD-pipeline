# 🍷 Microservice WineApp

This is a microservices-based Java application designed for wine management. Built using Spring Boot and Maven, integrated with SonarQube, Docker, and CI/CD via Jenkins.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Cloud (Config, Eureka, Gateway)
- Maven
- SonarQube
- Docker
- Jenkins
- GitHub

---

## 🏗️ Microservices Structure

| Microservice       | Description                      |
|--------------------|----------------------------------|
| wine-service       | Core service to manage wines     |
| order-service      | Handles customer orders          |
| inventory-service  | Manages stock and availability   |
| api-gateway        | Routes requests to services      |
| config-server      | Centralized config management    |
| discovery-server   | Service registration/discovery   |

---

## 🔧 Build and Run

```bash
# Build all services
mvn clean install

# Run individual service
cd wine-service
mvn spring-boot:run
