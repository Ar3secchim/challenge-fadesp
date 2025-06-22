CREATE TABLE pagamentos.pagamento (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  codigo_debito INT NOT NULL,
  identificador_pagamento VARCHAR(255) NOT NULL,
  metodo_pagamento VARCHAR(50) NOT NULL,
  numero_cartao VARCHAR(255),
  valor_pagamento DECIMAL(19, 2) NOT NULL,
  status_pagamento VARCHAR(50) NOT NULL,
  ativo BOOLEAN NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  data_atualizacao TIMESTAMP
);