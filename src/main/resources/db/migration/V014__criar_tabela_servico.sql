CREATE TABLE servico (
   codigo SERIAL PRIMARY KEY,
   descricao VARCHAR(50) NOT NULL,
   valor DECIMAL(10, 2) NOT NULL,
   duracao SMALLINT NOT NULL
);