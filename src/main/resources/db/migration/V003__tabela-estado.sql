CREATE TABLE estado (
	id BIGINT NOT NULL auto_increment primary key,
    nome VARCHAR(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado (nome) SELECT DISTINCT nome_estado FROM cidade;

ALTER TABLE cidade ADD COLUMN estado_id bigint NOT NULL;
UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);

ALTER TABLE cidade ADD CONSTRAINT fk_cidade_estado 
FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE cidade DROP COLUMN nome_estado;

ALTER TABLE cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;