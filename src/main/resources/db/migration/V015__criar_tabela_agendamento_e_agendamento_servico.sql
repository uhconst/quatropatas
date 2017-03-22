CREATE TABLE agendamento (
	codigo SERIAL PRIMARY KEY,
	data_criacao TIMESTAMP NOT NULL,
	data_agendamento TIMESTAMP NOT NULL,
	valor_desconto DECIMAL(10,2),
	valor_total DECIMAL(10,2) NOT NULL,
	status VARCHAR(30) NOT NULL,
	observacao VARCHAR(200)
);

CREATE TABLE agendamento_servico (
   codigo SERIAL PRIMARY KEY,
   valor_unitario DECIMAL(10,2) NOT NULL,
   codigo_agendamento BIGINT NOT NULL,
   codigo_servico BIGINT NOT NULL,
   codigo_animal BIGINT NOT NULL,
   FOREIGN KEY (codigo_agendamento) REFERENCES agendamento (codigo),
   FOREIGN KEY (codigo_servico) REFERENCES servico (codigo),
   FOREIGN KEY (codigo_animal) REFERENCES animal (codigo)
);