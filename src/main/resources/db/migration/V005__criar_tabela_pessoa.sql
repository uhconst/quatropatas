CREATE TABLE pessoa (
   codigo SERIAL PRIMARY KEY,
   codigo_cidade BIGINT NOT NULL,
   nome VARCHAR(40) NOT NULL,
   sobrenome VARCHAR(40) NOT NULL,
   sexo VARCHAR(25),
   nascimento VARCHAR(40),
   cpf VARCHAR(25),
   cep VARCHAR(15),
   bairro VARCHAR(50),
   logradouro VARCHAR(50),
   numero VARCHAR(15),
   complemento VARCHAR(20),
   FOREIGN KEY (codigo_cidade) REFERENCES cidade (codigo)
);
