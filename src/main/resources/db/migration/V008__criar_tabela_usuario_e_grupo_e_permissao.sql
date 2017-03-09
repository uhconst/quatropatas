CREATE TABLE usuario (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(50) NOT NULL,
	ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE grupo (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE permissao (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_grupo (
   codigo_usuario BIGINT NOT NULL,
   codigo_grupo BIGINT NOT NULL,
   PRIMARY KEY(codigo_usuario, codigo_grupo),
   FOREIGN KEY (codigo_usuario) REFERENCES usuario (codigo),
   FOREIGN KEY (codigo_grupo) REFERENCES grupo (codigo)
);

CREATE TABLE grupo_permissao (
   codigo_grupo BIGINT NOT NULL,
   codigo_permissao BIGINT NOT NULL,
   PRIMARY KEY(codigo_grupo, codigo_permissao),
   FOREIGN KEY (codigo_grupo) REFERENCES grupo (codigo),
   FOREIGN KEY (codigo_permissao) REFERENCES permissao (codigo)
);