CREATE TABLE telefone (
   codigo SERIAL PRIMARY KEY,
   codigo_pessoa BIGINT NOT NULL,
   numero VARCHAR(25) NOT NULL,
   FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (codigo)
);

