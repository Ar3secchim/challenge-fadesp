CREATE TABLE pagamento (
 id SERIAL PRIMARY KEY,
 codigo_debito INTEGER NOT NULL,
 indentificador_pagamento VARCHAR(20) NOT NULL,
 metodo_pagamento VARCHAR(30) NOT NULL,
 numero_cartao VARCHAR(30),
 valor_pagamento NUMERIC(12, 2) NOT NULL,
 status VARCHAR(30) NOT NULL,
);