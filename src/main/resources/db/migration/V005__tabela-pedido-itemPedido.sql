CREATE TABLE pedido (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subtotal DECIMAL(10,2),
    taxa_frete DECIMAL(10,2),
    valor_total DECIMAL(10,2),

    usuario_cliente_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,

    endereco_cidade_id BIGINT(20) NOT NULL,
    endereco_cep VARCHAR(9) NOT NULL,
    endereco_logradouro VARCHAR(100) NOT NULL,
    endereco_numero VARCHAR(20) NOT NULL,
    endereco_complemento VARCHAR(60) NOT NULL,
    endereco_bairro VARCHAR(60) NOT NULL,

    status VARCHAR(10) NOT NULL,
    data_criacao DATETIME NOT NULL,
    data_confirmacao DATETIME,
    data_cancelamento DATETIME,
    data_entrega DATETIME,

    CONSTRAINT fk_pedido_endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id),
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
    CONSTRAINT fk_pedido_usuario_cliente FOREIGN KEY (usuario_cliente_id) REFERENCES usuario (id),
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_pedido (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quantidade SMALLINT(6) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    preco_total DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(255) NOT NULL,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,

    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;