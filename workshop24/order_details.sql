DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details (

    id INTEGER NOT NULL,
    product varchar(64) NOT NULL,
    unit_price DECIMAL(3, 2) NOT NULL,
    discount DECIMAL(2, 2),
    quantity INTEGER NOT NULL,

    PRIMARY KEY (id, product),
    FOREIGN KEY (id) REFERENCES orders(order_id)
)