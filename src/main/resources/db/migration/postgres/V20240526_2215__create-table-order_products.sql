CREATE TABLE order_products
(
    order_id integer,
    product_id integer,
    product_quantity integer,
    CONSTRAINT pk_order_products PRIMARY KEY (order_id, product_id)
);