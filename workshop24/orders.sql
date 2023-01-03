DROP TABLE IF EXISTS orders;
CREATE TABLE orders (

    order_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    order_date DATE NOT NULL,
    customer_name VARCHAR(128) NOT NULL,
    ship_address VARCHAR(128) NOT NULL,
    notes TEXT,
    tax DECIMAL(2, 2) DEFAULT 0.05
);