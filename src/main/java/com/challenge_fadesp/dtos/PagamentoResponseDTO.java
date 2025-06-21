package com.challenge_fadesp.dtos;

import com.challenge_fadesp.model.MetodoPagamento;
import com.challenge_fadesp.model.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoResponseDTO {
  private Long id;
  private Integer codigoDebito;
  private String identificadorPagamento;
  private MetodoPagamento metodoPagamento;
  private String numeroCartao;
  private BigDecimal valorPagamento;
  private StatusPagamento statusPagamento;
  private Boolean ativo;
}