package com.challenge_fadesp.dtos;

import com.challenge_fadesp.model.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusDTO {
  private StatusPagamento statusPagamento;
}
