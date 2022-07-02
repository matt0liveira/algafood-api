INSERT INTO cozinha (nome) VALUES ('Tailandesa');
INSERT INTO cozinha (nome) VALUES ('Indiana');

INSERT INTO restaurante (nome, taxa_frete, fk_cozinha) VALUES ('Baka', 7.0, 1);

INSERT INTO forma_pagamento (descricao) VALUES ('Dinheiro');
INSERT INTO forma_pagamento (descricao) VALUES ('Crédito');

INSERT INTO estado (nome) VALUES('SP');
INSERT INTO estado (nome) VALUES('RJ');

INSERT INTO cidade (nome, fk_estado) VALUES ('Tatuí', 1);

INSERT INTO permissao (nome, descricao) VALUES ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permissao (nome, descricao) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas');