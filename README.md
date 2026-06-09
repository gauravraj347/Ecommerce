# eCommerce Microservices Backend

A backend project built with Java and Spring Boot that started as a simple eCommerce app and evolved into a full microservices architecture. Built this while learning Spring Boot, Docker, and Kubernetes.

## What this project does

It's a backend for an eCommerce platform where users can register, browse products, add items to a cart, and place orders. The whole system is split into separate services that talk to each other.

## Services

- **User Service** – handles user registration and profiles
- **Product Service** – manages the product catalogue
- **Order Service** – handles cart and order placement
- **API Gateway** – single entry point that routes requests to the right service
- **Eureka Server** – keeps track of all running services
- **Config Server** – stores configuration for all services in one place

## Tech Stack

- Java 21, Spring Boot 4.0
- Spring Data JPA + Hibernate (database layer)
- PostgreSQL (main database), H2 (for testing)
- Spring Cloud Gateway (API Gateway)
- Netflix Eureka (service discovery)
- OpenFeign + RestTemplate (services talking to each other)
- RabbitMQ + Apache Kafka (async messaging)
- OAuth2 + Keycloak (authentication and security)
- Docker + Docker Compose (containerisation)
- Kubernetes (deployment)
- Spring Boot Actuator + Zipkin (monitoring and tracing)

## How to run

**Using Docker Compose (easiest way)**

```bash
git clone https://github.com/gauravraj347/Ecommerce.git
cd Ecommerce
docker-compose up --build
```

This starts everything — all services, PostgreSQL, RabbitMQ, and Zipkin together.

**Running manually**

Start services in this order:
1. Eureka Server
2. Config Server
3. User, Product, Order services
4. API Gateway

```bash
cd ecommerce
mvn spring-boot:run
```
