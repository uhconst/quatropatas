ALTER TABLE agendamento ADD COLUMN codigo_pessoa BIGINT;

ALTER TABLE agendamento 
   ADD CONSTRAINT fk_codigo_pessoa
   FOREIGN KEY (codigo_pessoa) 
   REFERENCES pessoa(codigo);
