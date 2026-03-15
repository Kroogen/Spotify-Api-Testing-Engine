# Spotify API Testing Engine 🎵

A robust, enterprise-level test automation framework for the Spotify Web API. This project validates the core
functionalities of the Spotify API using modern automation practices, focusing on full CRUD lifecycles, stateful
testing, and secure OAuth 2.0 token management.

## 🚀 Tech Stack

* **Language:** Java 17
* **API Testing:** REST Assured 6.0
* **Test Runner:** TestNG (Parallel Execution: Classes & DataProviders)
* **Data Binding (POJOs):** Jackson & Lombok
* **Build Tool:** Maven
* **Reporting:** Allure Reports
* **Logging:** Log4j2}
* **CI/CD:** GitHub Actions

## 🎯 Key Features

* **OAuth 2.0 Automation:** Automated generation and caching of Bearer tokens using `refresh_token` grants.
* **E2E CRUD Lifecycle:** Implements stateful testing to Create, Read, Update, and Unfollow (Delete) Spotify Playlists
  sequentially.
* **State Verification:** Validates `PUT` requests by immediately performing `GET` requests to ensure data persistence.
* **Soft Assertions:** Utilizes TestNG `SoftAssert` to validate multiple JSON fields simultaneously without aborting the
  test prematurely.
* **Singleton Configuration:** Centralized `ConfigLoader` to securely manage environment variables and credentials.
* **Data-Driven Testing (DDT):** Utilizes TestNG `@DataProvider` to validate search boundaries across Artists, Albums,
  and Tracks.
* **Contract Testing:** Implements JSON Schema Validation to ensure API structural integrity.
* **Thread-Safe Parallelism:** Multi-threaded execution at the class and data-provider level, optimized for CI/CD speed.
* **Observability:** Comprehensive logging with Log4j2 and interactive Allure reports with embedded HTTP
  request/response attachments.

## 🏗️ Project Architecture

The framework follows a modular and scalable design:

* `api/`: Contains route definitions and HTTP methods (`PlaylistApi`, `AccountApi`) and the base configurations (
  `SpecBuilder`).
* `models/`: Contains the POJOs (Plain Old Java Objects) built with Lombok for serializing and deserializing JSON
  payloads.
* `tests/`: Contains the TestNG execution classes with strict priority dependencies.
* `utils/`: Contains helper classes for Token Management and Configuration loading.
* `resources/schema/`: JSON Schema blueprints for contract validation.

## ⚙️ Setup and Execution

### 1. Prerequisites

* Java JDK 17+
* Maven installed
* A Spotify Developer Account (to obtain Client ID and Client Secret)

### 2. Configuration

To protect sensitive data, credentials are not stored in the repository.

1. Clone the repository.
2. Navigate to `src/main/resources/`.
3. Copy the `config.properties.example` file and rename it to `config.properties`.
4. Fill in your Spotify Developer credentials in the new `config.properties` file:
   ```properties
   client_id=your_id
   client_secret=your_secret
   refresh_token=your_token
   user_id=your_user
   base_url=[https://api.spotify.com/v1](https://api.spotify.com/v1)
   accounts_url=[https://accounts.spotify.com](https://accounts.spotify.com)

### 3. Running the Tests

   ```properties
    mvn clean test
    mvn allure:serve