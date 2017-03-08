CREATE TABLE email (
   codigo SERIAL PRIMARY KEY,
   codigo_pessoa BIGINT NOT NULL,
   endereco VARCHAR(50) NOT NULL,
   FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (codigo)
);

