
# Hotel Management System - Restaurant Management Module

This project is part of a Hotel Management System aimed at helping hotel owners manage various hotel services efficiently. The **Restaurant Management Module** is focused on managing restaurant operations, including adding restaurants and tables, handling reservations, and providing ratings at the end of the day.

## Tech Stack

- **Frontend**: Angular
- **Backend**: Spring Boot
- **Database**: MySQL
- **Microservices**: The system follows a microservices architecture for modular development and scalability.
- **JDK**: 17

## Features

### Restaurant Management

- **Add Restaurant**: The ability to add a new restaurant.
- **Manage Tables**: Add, update, remove tables and reserve them for customers.
- **Reservations**: Handle customer reservations.
- **Restaurant Ratings**: Customers can rate the restaurant after dining.

### CRUD Operations

- **Create**: Add new restaurants, tables, reservations, and ratings.
- **Read**: Retrieve details of restaurants, tables, reservations, and ratings.
- **Update**: Modify restaurant, table, reservation, or rating details.
- **Delete**: Remove restaurants, tables, reservations, or ratings.

## Installation

To run this project locally:

### Prerequisites

- **JDK 17** or later
- **MySQL**
- **Maven**

### Steps

1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```

2. Set up the MySQL database and configure the connection in `application.properties` (Spring Boot).

3. Run the backend Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. Install the frontend dependencies:
   ```bash
   cd frontend
   npm install
   ```

5. Start the Angular frontend:
   ```bash
   ng serve
   ```

6. Open the application in your browser at `http://localhost:4200`.

## API Endpoints

### Restaurants

- **GET** `/restaurants/retrieve-all-restaurants`: Get all restaurants
- **POST** `/restaurants/add-restaurant`: Add a new restaurant
- **PUT** `/restaurants/update-restaurant`: Update restaurant details
- **DELETE** `/restaurants/remove-restaurant/{id}`: Remove a restaurant
- **GET** `/restaurants/retrieve-restaurant/{id}`: Get a specific restaurant

### Tables

- **POST** `/restaurants/tables/add-table`: Add a new table
- **GET** `/restaurants/tables/retrieve-all-tables`: Get all tables
- **DELETE** `/restaurants/tables/remove-table/{id}`: Remove a table
- **GET** `/restaurants/tables/retrieve-table/{id}`: Get a specific table
- **POST** `/restaurants/tables/reserve-table/{id}`: Reserve a specific table
- **PUT** `/restaurants/tables/update-table`: Update table details

### Ratings

- **POST** `/restaurants/rating/add-rating`: Add a new rating for the restaurant
- **GET** `/restaurants/rating/get-all-ratings`: Get all restaurant ratings
- **GET** `/restaurants/rating/retrieve-rating/{id}`: Retrieve a specific rating
- **GET** `/restaurants/rating/get-ratings-by-restaurant/{restaurantId}`: Get all ratings for a specific restaurant

## Contributing

Feel free to extend and improve this module! If you're working on another part of the hotel management system, make sure to follow the structure and guidelines in this README.

To contribute, please fork the repository, make your changes, and submit a pull request.
