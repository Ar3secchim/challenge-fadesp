-- Remove enums antigos (do schema public)
DROP TABLE IF EXISTS pagamentos.pagamento CASCADE;
DROP TYPE IF EXISTS public.metodo_pagamento_enum CASCADE;
DROP TYPE IF EXISTS public.status_pagamento_enum CASCADE;

-- Cria os enums dentro do schema pagamentos
CREATE TYPE pagamentos.metodo_pagamento_enum AS ENUM (
    'BOLETO',
    'PIX',
    'CARTAO_CREDITO',
    'CARTAO_DEBITO'
    );

CREATE TYPE pagamentos.status_pagamento_enum AS ENUM (
    'PENDENTE_PROCESSAMENTO',
    'PROCESSADO_SUCESSO',
    'PROCESSADO_FALHA'
    );

CREATE TABLE IF NOT EXISTS pagamentos.pagamento
(
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
