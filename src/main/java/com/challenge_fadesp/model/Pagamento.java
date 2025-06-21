package com.challenge_fadesp.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "pagamento", schema = "pagamentos")
public class Pagamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "codigo_debito", nullable = false)
  private Integer codigoDebito;

  @Column(name = "identificador_pagamento", nullable = false, length = 20)
  private String identificadorPagamento;

  @Enumerated(EnumType.STRING)
  @Column(name = "metodo_pagamento", nullable = false)
  private MetodoPagamento metodoPagamento;

  @Column(name = "numero_cartao")
  private String numeroCartao;

  @Column(name = "valor_pagamento", nullable = false)
  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private StatusPagamento status;
}
