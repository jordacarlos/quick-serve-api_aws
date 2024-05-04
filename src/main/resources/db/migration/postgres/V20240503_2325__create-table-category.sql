CREATE TABLE category
(	
	id BIGSERIAL UNIQUE,
    code character varying(4),
    description character varying(100),
    CONSTRAINT pk_category_id PRIMARY KEY (id)
);