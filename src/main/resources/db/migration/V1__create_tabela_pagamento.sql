CREATE SCHEMA IF NOT EXISTS pagamentos;

-- Criação dos tipos ENUM
CREATE TYPE metodo_pagamento_enum AS ENUM (
    'BOLETO',
    'PIX',
    'CARTAO_CREDITO',
    'CARTAO_DEBITO'
    );

CREATE TYPE status_pagamento_enum AS ENUM (
    'PENDENTE_PROCESSAMENTO',
    'PROCESSADO_SUCESSO',
    'PROCESSADO_FALHA'
);

-- Criação da tabela de pagamento
CREATE TABLE IF NOT EXISTS pagamentos.pagamento (
                                                    id                      BIGINT PRIMARY KEY,
                                                    codigo_debito           INT                   NOT NULL,
                                                    identificador_pagamento VARCHAR(20)           NOT NULL,
                                                    metodo_pagamento        metodo_pagamento_enum NOT NULL,
                                                    numero_cartao           VARCHAR(20),
                                                    valor_pagamento         DECIMAL(10, 2)        NOT NULL,
                                                    status                  status_pagamento_enum NOT NULL,
                                                    ativo                   BOOLEAN               NOT NULL DEFAULT TRUE,
                                                    data_criacao            TIMESTAMP             NOT NULL,
                                                    data_atualizacao        TIMESTAMP
);