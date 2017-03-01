CREATE TABLE animal (
   codigo SERIAL PRIMARY KEY,
   codigo_raca BIGINT NOT NULL,
   nome VARCHAR(50) NOT NULL,
   sexo VARCHAR(50) NOT NULL,
   FOREIGN KEY (codigo_raca) REFERENCES raca (codigo)
);

