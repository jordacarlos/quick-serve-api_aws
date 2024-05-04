CREATE TABLE customer
(	
	id BIGSERIAL UNIQUE,
    name character varying(100),    
    email character varying(100),    
	cpf character varying(14),
    CONSTRAINT pk_customer_id PRIMARY KEY (id)
);