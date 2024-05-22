CREATE TABLE orders
(
    id BIGSERIAL UNIQUE,
    name character varying(100),
    status character varying(100)
);