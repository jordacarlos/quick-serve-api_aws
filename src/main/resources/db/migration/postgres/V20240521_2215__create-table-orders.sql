CREATE TABLE orders
(
    id BIGSERIAL UNIQUE,
    status character varying(100),
    customer_id character varying(100),
    total_order_value double precision DEFAULT 0.0,
    CONSTRAINT pk_order_id PRIMARY KEY (id)
);