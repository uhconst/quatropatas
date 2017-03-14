ALTER TABLE animal ADD COLUMN codigo_pessoa BIGINT;

ALTER TABLE animal 
   ADD CONSTRAINT fk_codigo_pessoa
   FOREIGN KEY (codigo_pessoa) 
   REFERENCES pessoa(codigo);
