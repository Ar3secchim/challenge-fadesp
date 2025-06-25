package com.challenge_fadesp.domain.entity;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Pagamento representa um pagamento realizado por um usuário.
 * Contém informações sobre o pagamento, como código de débito, CPF/CNPJ do pagador,
 * método de pagamento, número do cartão, valor do pagamento, status do pagamento,
 * e timestamps de criação e atualização.
 */
@Entity
@Table(name = "pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "codigo_debito", nullable = false)
  private Integer codigoDebito;

  @Column(name = "identificador_pagamento", nullable = false)
  private String identificadorPagamento;

  @Enumerated(EnumType.STRING)
  @Column(name = "metodo_pagamento", nullable = false)
  private MetodoPagamento metodoPagamento;

  @Column(name = "numero_cartao")
  private String numeroCartao;

  @Column(name = "valor_pagamento", nullable = false)
  private BigDecimal valorPagamento;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pagamento", nullable = false)
  private StatusPagamento statusPagamento;

  @Column(name = "ativo", nullable = false)
  private Boolean ativo;

  @Column(name = "data_criacao", nullable = false)
  private LocalDateTime dataCriacao;

  @Column(name = "data_atualizacao")
  private LocalDateTime dataAtualizacao;

  @PrePersist
  public void prePersist() {
    this.dataCriacao = LocalDateTime.now();
    this.ativo = true;
    this.statusPagamento = StatusPagamento.PENDENTE_PROCESSAMENTO;
  }

  @PreUpdate
  public void preUpdate() {
    this.dataAtualizacao = LocalDateTime.now();
  }
}