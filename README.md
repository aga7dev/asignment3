# Car Rental System

## About the project
This is a simple console application written in Java.  
The project was made for a university assignment to practice working with databases and object-oriented programming.

The program allows managing cars, customers and rental contracts.  
All data is stored in a PostgreSQL database and accessed using JDBC.



## What was used
- Java  
- PostgreSQL  
- JDBC  
- IntelliJ IDEA  
- pgAdmin  



## Project overview
The project is divided into several parts to keep the code more organized.

- `model` – classes that describe cars, customers and rentals  
- `repository` – database access and SQL queries  
- `service` – business logic and validation  
- `controller` – console menu and user input  
- `utils` – database connection  
- `exception` – custom exceptions  
- `resources` – SQL schema file  



## Database
PostgreSQL is used as the database.

The file `schema.sql`:
- creates all required tables
- defines primary and foreign keys
- inserts a few test records

Tables used:
- cars
- customers
- rentals



## Program features
- Add and view cars
- Add and view customers
- Create and delete rental contracts
- Calculate rental price based on dates
- Prevent renting unavailable cars
- Basic error handling



## How to run
1. Create a PostgreSQL database (for example `car_rental_db`)
2. Run `schema.sql` in pgAdmin (only once)
3. Check database credentials in `DatabaseConnection.java`
4. Open the project in IntelliJ IDEA
5. Run `Main.java`
6. Use the console menu to work with the system




## Notes
- The project uses plain JDBC without any frameworks
- The application works in the terminal
- The main goal of the project was to understand Java, SQL and OOP basics


