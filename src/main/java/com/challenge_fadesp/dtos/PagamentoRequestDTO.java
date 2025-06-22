package com.challenge_fadesp.dtos;

import com.challenge_fadesp.model.enums.MetodoPagamento;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequestDTO {
  private Integer codigoDebito;
  private String identificadorPagamento;
  private MetodoPagamento metodoPagamento;
  private String numeroCartao;
  private BigDecimal valorPagamento;
}
