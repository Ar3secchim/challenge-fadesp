package com.challenge_fadesp.dtos;

import com.challenge_fadesp.model.StatusPagamento;
import lombok.Getter;

@Getter
public class AtualizarStatusDTO {
  private Long id;
  private StatusPagamento statusPagamento;
}
