CREATE TABLE products
(
    id BIGSERIAL UNIQUE,
    name character varying(50),
    category character varying(30),
    description character varying(100),
    price double precision DEFAULT 0.0,
    image_path character varying(100),
    CONSTRAINT pk_products_id PRIMARY KEY (id)
);