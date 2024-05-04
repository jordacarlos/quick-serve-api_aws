CREATE TABLE product
(	
	id BIGSERIAL UNIQUE,
	category_id bigint,
    code character varying(4),
    description character varying(100),
    price double precision DEFAULT 0.0,
    CONSTRAINT pk_product_id PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES category(id)
);