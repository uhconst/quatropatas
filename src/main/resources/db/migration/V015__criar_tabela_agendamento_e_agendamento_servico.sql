CREATE TABLE agendamento (
	codigo SERIAL PRIMARY KEY,
	codigo_pessoa BIGINT NOT NULL,
	data_agendamento DATE,
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (codigo)
);

CREATE TABLE agendamento_servico (
   codigo_agendamento BIGINT NOT NULL,
   codigo_servico BIGINT NOT NULL,
   PRIMARY KEY(codigo_agendamento, codigo_servico),
   FOREIGN KEY (codigo_agendamento) REFERENCES agendamento (codigo),
   FOREIGN KEY (codigo_servico) REFERENCES servico (codigo)
);