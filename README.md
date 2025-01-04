# **Order Service Documentation**

## Overview

The Order Service is a Spring Boot-based application that interacts with the Catalog Service to manage book orders. 
It allows users to place orders for books, view the status of their orders, and perform CRUD operations on orders. 
The service integrates with the Catalog Service to fetch book details and use them while placing orders.

## Features
- Place Orders: Users can place orders for books available in the catalog.
- Order Status: The system tracks the status of each order, from creation to fulfillment.
- Search Orders: Allows searching for orders based on different criteria, such as order ID, book title, or status.
- Order Management: Provides functionality to update and delete orders.


## Architecture
- Domain Layer: Represents the order entity with attributes such as order ID, book information, quantity, and order status.
- Repository Layer: Interfaces with the database for performing CRUD operations on orders using Spring Data JPA.
- Service Layer: Contains the business logic for order processing and interactions with the Catalog Service.
- Controller Layer: Exposes the REST API for interacting with orders.
- Integration Layer: Communicates with the Catalog Service to fetch book details when creating orders.


# Project Wiki: Book Store

Leitet von den folgenden Anforderungen Epics ab:

- Ein benutzerfreundliches Interface für die Buchsuche und den Kaufprozess
- Eine persönliche Bibliothek für registrierte Nutzer, um gekaufte Bücher zu verwalten
- Empfehlungsalgorithmen, die auf dem Leseverhalten der Nutzer basieren
- Eine sichere und reibungslose Zahlungsabwicklung
- Ein einfach zu bedienendes Backend für die Verwaltung von Büchern, Bestellungen und Nutzern

## Bounded Context for Requirements

###### *Based on the requirements provided, the following bounded contexts can be identified for the book search and purchasing system:*

### User Interface Context

- Responsible for the book search and purchase process.
- Manages user interactions and displays relevant information.

### Personal Library Context

- Manages the personal library of registered users.
- Handles the organization and retrieval of purchased books.

### Recommendation Engine Context

- Analyzes user reading behavior to generate personalized book recommendations.
- Interacts with user data to improve suggestions.

### Payment Processing Context

- Handles secure payment transactions and order processing.
- Ensures compliance with security standards for financial data.

### Backend Management Context

- Manages the administration of books, orders, and user accounts.
- Provides tools for inventory management and reporting.

## Modeling Bounded Contexts

*The following diagram illustrates the bounded contexts and their relationships:*

![image](https://github.com/user-attachments/assets/d8c93b4b-3dd8-4a02-af6f-63369762c9e5)

## Defining Essential Entities

### User Interface Context

###### *Entities:*

- User
- Search Query
- Book Listing

###### Dependencies:

Relies on the Recommendation Engine for personalized suggestions.

### Personal Library Context

###### Entities:

- User Library
- Book Item

###### Dependencies:

- Depends on Backend Management for book details.

### Recommendation Engine Context

###### Entities:

- User Profile
- Reading History
- Recommendations

###### Dependencies:

- Requires data from User Interface and Personal Library contexts.

### Payment Processing Context

###### Entities:

- Payment Transaction
- Order Confirmation

###### Dependencies:

- Interacts with Backend Management for order details.

### Backend Management Context

###### Entities:

- Book Inventory
- User Accounts
- Order Records




###### Dependencies:

- Supports all other contexts by providing necessary data.

# Defining Interactions Between Bounded Contexts (Context Map)

###### *The context map outlines how these bounded contexts interact:*

##### User Interface Context <-->  Personal Library Context

The User Interface requests book details from the Personal Library to display to users.

##### User Interface Context <--> Recommendation Engine Context

The User Interface sends user preferences to the Recommendation Engine to fetch personalized recommendations.

##### User Interface Context <--> Payment Processing Context

The User Interface initiates payment requests and receives transaction confirmations.

##### Backend Management <--> All Other Contexts

Acts as a central hub for data exchange, ensuring that all contexts have access to up-to-date information about users,
books, and orders.

##### Payment Processing <--> Backend Management

Payment processing confirms transactions and updates order records in the Backend Management context.


## Core Components
### Order Entity
The Order entity defines the structure of an order, including fields such as order ID, book, quantity, status, and timestamp. This entity is mapped to a database table using JPA annotations.

### Order Repository
The OrderRepository interface extends JpaRepository and provides methods for interacting with the orders in the database. It supports finding orders by their ID, status, or associated book.

### Order Service
The OrderService contains business logic for creating and managing orders. It interacts with the Catalog Service to fetch book details and validate book availability before placing an order.

### Order Controller
The OrderController exposes API endpoints for creating, viewing, updating, and searching orders. It provides endpoints for placing new orders, retrieving order statuses, and searching orders based on various criteria.


# CI/CD Pipeline

The Order Service also integrates with GitHub Actions for continuous integration and deployment (CI/CD). The pipeline for the Order Service follows the same steps as the Catalog Service, with a few differences in building the order service, testing, and deployment.

1. Code Checkout: Latest code is fetched from the repository.
2. Set up Java: Java 21 is configured for building the application.
3. Cache Maven Dependencies: Caches Maven dependencies for faster build times.
4. Build the Application: The application is built using Maven.
5. Start the Application: The Order Service is started for testing.
6. Run Unit Tests: Executes unit tests for ensuring the service functions correctly.
7. Run Load Tests: Executes load tests to simulate high traffic on order endpoints.
8. Build and Push Docker Image: Docker image for the Order Service is built and pushed to DockerHub.
9. Stop Application: Stops the service after the tests are done.
10. Generate Code Coverage Reports: Uses JaCoCo to generate code coverage reports.


# Docker-Compose Configuration
In the docker-compose.yml file, the Order Service is defined as a separate service that depends on the Catalog Service. The CATALOG_SERVICE_URL environment variable points to the Catalog Service's internal URL (http://catalog:8080). Docker Compose simplifies the deployment of both services together.


# Testing
### Unit Testing
Unit tests for the Order Service validate the functionality of individual components, such as the OrderRepository, OrderService, and OrderController. These tests ensure that the order creation and management processes are working as expected.

### Controller Testing
Controller tests simulate HTTP requests to the OrderController and verify the responses. These tests check that the order-related API endpoints work correctly, such as creating orders, retrieving orders, and updating the order status.

### Load Testing
Load tests are executed using Gatling, simulating high traffic for order-related operations. The test ensures that the system can handle a significant number of users placing orders simultaneously.





