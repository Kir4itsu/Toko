CREATE DATABASE IF NOT EXISTS Toko;

USE Toko;

-- Create the Users table
CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'seller', 'customer') NOT NULL
);

-- Create the Products table
CREATE TABLE IF NOT EXISTS Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

-- Create the Orders table
CREATE TABLE IF NOT EXISTS Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES Users(id),
    FOREIGN KEY(product_id) REFERENCES Products(id)
);

-- Insert User Login
INSERT INTO users (username, password, role) VALUES ('Kir4itsu', 'Kir4itsu', 'Admin');
INSERT INTO users (username, password, role) VALUES ('Kir', 'Kir', 'Seller');
INSERT INTO users (username, password, role) VALUES ('Kiraitsu', 'Kiraitsu', 'Customer');

-- Insert Products
INSERT INTO Products (name, price, stock) VALUES ('Product1', 10.900, 100);
INSERT INTO Products (name, price, stock) VALUES ('Product2', 15.500, 200);
INSERT INTO Products (name, price, stock) VALUES ('Product3', 7.750, 150);
INSERT INTO Products (name, price, stock) VALUES ('Product4', 20.000, 50);
INSERT INTO Products (name, price, stock) VALUES ('Product5', 5.250, 300);