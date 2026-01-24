DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS customers;

CREATE TABLE cars (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      plate_number VARCHAR(20) NOT NULL UNIQUE,
                      daily_rate NUMERIC(10,2) NOT NULL CHECK (daily_rate > 0),
                      status VARCHAR(20) NOT NULL
);

CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(120) UNIQUE
);

CREATE TABLE rentals (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(120) NOT NULL,
                         car_id INT NOT NULL REFERENCES cars(id),
                         customer_id INT NOT NULL REFERENCES customers(id),
                         start_date DATE NOT NULL,
                         end_date DATE NOT NULL,
                         total_price NUMERIC(10,2) NOT NULL CHECK (total_price >= 0)
);

INSERT INTO cars(name, plate_number, daily_rate, status) VALUES
                                                             ('Toyota Camry', 'A123BC', 25000, 'AVAILABLE'),
                                                             ('Hyundai Elantra', 'K777KK', 18000, 'AVAILABLE');

INSERT INTO customers(name, email) VALUES
                                       ('Aruzhan S.', 'aruzhan@mail.com'),
                                       ('Dias K.', 'dias@mail.com');
