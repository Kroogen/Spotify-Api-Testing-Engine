# Spotify API Testing Engine 🎵

A robust, enterprise-level test automation framework for the Spotify Web API. This project validates the core
functionalities of the Spotify API using modern automation practices, focusing on full CRUD lifecycles, stateful
testing, and secure OAuth 2.0 token management.

## 🚀 Tech Stack

* **Language:** Java 17
* **API Testing:** REST Assured
* **Testing Framework:** TestNG
* **Data Binding (POJOs):** Jackson & Lombok
* **Build Tool:** Maven

## 🎯 Key Features

* **OAuth 2.0 Automation:** Automated generation and caching of Bearer tokens using `refresh_token` grants.
* **E2E CRUD Lifecycle:** Implements stateful testing to Create, Read, Update, and Unfollow (Delete) Spotify Playlists
  sequentially.
* **State Verification:** Validates `PUT` requests by immediately performing `GET` requests to ensure data persistence.
* **Soft Assertions:** Utilizes TestNG `SoftAssert` to validate multiple JSON fields simultaneously without aborting the
  test prematurely.
* **Singleton Configuration:** Centralized `ConfigLoader` to securely manage environment variables and credentials.

## 🏗️ Project Architecture

The framework follows a modular and scalable design:

* `api/`: Contains route definitions and HTTP methods (`PlaylistApi`, `AccountApi`) and the base configurations (
  `SpecBuilder`).
* `models/`: Contains the POJOs (Plain Old Java Objects) built with Lombok for serializing and deserializing JSON
  payloads.
* `tests/`: Contains the TestNG execution classes with strict priority dependencies.
* `utils/`: Contains helper classes for Token Management and Configuration loading.

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
   client_id=YOUR_CLIENT_ID
   client_secret=YOUR_CLIENT_SECRET
   refresh_token=YOUR_REFRESH_TOKEN
   user_id=YOUR_SPOTIFY_USER_ID
   ...

### 3. Running the Tests

   ```properties
    mvn clean test