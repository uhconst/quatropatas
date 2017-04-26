ALTER TABLE agendamento ADD COLUMN codigo_usuario BIGINT;

ALTER TABLE agendamento 
   ADD CONSTRAINT fk_codigo_usuario
   FOREIGN KEY (codigo_usuario) 
   REFERENCES usuario(codigo);
