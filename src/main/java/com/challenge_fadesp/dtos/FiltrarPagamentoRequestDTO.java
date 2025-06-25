package com.challenge_fadesp.dtos;

import com.challenge_fadesp.domain.enums.StatusPagamento;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltrarPagamentoRequestDTO {
  private Integer codigoDebito;
  private String identificadorPagamento;
  private StatusPagamento statusPagamento;
}
