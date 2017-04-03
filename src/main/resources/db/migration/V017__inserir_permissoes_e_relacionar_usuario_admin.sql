INSERT INTO permissao(nome) VALUES('ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao(nome) VALUES('ROLE_CADASTRAR_CIDADE');
INSERT INTO permissao(nome) VALUES('ROLE_CADASTRAR_SERVICO');

INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES(1, 1);
INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES(1, 2);
INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES(1, 3);

INSERT INTO usuario_grupo(codigo_usuario, codigo_grupo) VALUES(
	(SELECT codigo FROM usuario WHERE email ='admin@quatropatas.com'), 1);