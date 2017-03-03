CREATE TABLE pais (
   codigo SERIAL PRIMARY KEY,
   nome VARCHAR(40) NOT NULL,
   sigla CHAR(3)
);


CREATE TABLE estado (
   codigo SERIAL PRIMARY KEY,
   codigo_pais BIGINT NOT NULL,
   nome VARCHAR(40) NOT NULL,
   sigla CHAR(2),
   FOREIGN KEY (codigo_pais) REFERENCES pais (codigo)
);

CREATE TABLE cidade (
   codigo SERIAL PRIMARY KEY,
   codigo_estado BIGINT NOT NULL,
   nome VARCHAR(40) NOT NULL,
   FOREIGN KEY (codigo_estado) REFERENCES estado (codigo)
);

--  ### INSERINDO BRASIL NO PAIS ##
INSERT INTO pais (nome, sigla)
VALUES ('Brasil', 'BRA');

--  ###  INSERINDO TODOS OS ESTADOS BRASILEIROS  ##
Insert Into estado (codigo_pais,sigla,nome) Values(1,'AC','Acre');  
Insert Into estado (codigo_pais,sigla,nome) Values(1,'AL','Alagoas');  
Insert Into estado (codigo_pais,sigla,nome) Values(1,'AM','Amazonas');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'AP','Amapá');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'BA','Bahia');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'CE','Ceará');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'DF','Distrito Federal');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'ES','Espírito Santo');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'GO','Goiás');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'MA','Maranhão');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'MG','Minas Gerais');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'MS','Mato Grosso do Sul');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'MT','Mato Grosso');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'PA','Pará');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'PB','Paraíba');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'PE','Pernambuco');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'PI','Piauí');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'PR','Paraná');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'RJ','Rio de Janeiro');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'RN','Rio Grande do Norte');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'RO','Rondônia');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'RR','Roraima');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'RS','Rio Grande do Sul');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'SC','Santa Catarina');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'SE','Sergipe');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'SP','São Paulo');
Insert Into estado (codigo_pais,sigla,nome) Values(1,'TO','Tocantins');


--  ###  INSERINDO ALGUMAS CIDADES  ##
Insert Into cidade (codigo_estado,nome) Values(26,'Catanduva');
Insert Into cidade (codigo_estado,nome) Values(26,'São José do Rio Preto');
Insert Into cidade (codigo_estado,nome) Values(26,'Ribeirão Preto');
Insert Into cidade (codigo_estado,nome) Values(1,'Rio Branco');
Insert Into cidade (codigo_estado,nome) Values(1,'Cruzeiro do Sul');
