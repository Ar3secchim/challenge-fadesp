CREATE SCHEMA IF NOT EXISTS pagamentos;

-- Criação dos tipos ENUM
CREATE TYPE metodo_pagamento_enum AS ENUM (
    'BOLETO',
    'PIX',
    'CARTAO_CREDITO',
    'CARTAO_DEBITO'
    );

CREATE TYPE status_pagamento_enum AS ENUM (
    'PENDENTE',
    'SUCESSO',
    'FALHA'
);

-- Criação da tabela de pagamento
CREATE TABLE IF NOT EXISTS pagamentos.pagamento (
     id SERIAL PRIMARY KEY,
     codigo_debito INTEGER NOT NULL,
     indentificador_pagamento VARCHAR(20) NOT NULL,
     metodo_pagamento metodo_pagamento_enum NOT NULL,
     numero_cartao VARCHAR(30),
     valor_pagamento NUMERIC(12, 2) NOT NULL,
     status status_pagamento_enum NOT NULL
);