package com.challenge_fadesp.dtos;

import com.challenge_fadesp.model.MetodoPagamento;
import com.challenge_fadesp.model.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoResponseDTO {
  private Long id;
  private Integer codigoDebito;
  private String identificadorPagamento;
  private MetodoPagamento metodoPagamento;
  private String numeroCartao;
  private BigDecimal valor;
  private StatusPagamento status;
  private Boolean ativo;
}