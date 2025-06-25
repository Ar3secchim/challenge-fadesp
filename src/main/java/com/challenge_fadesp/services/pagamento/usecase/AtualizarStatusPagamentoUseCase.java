package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.domain.enums.StatusPagamento;

public interface AtualizarStatusPagamentoUseCase {
  PagamentoResponseDTO execute(Long id, StatusPagamento novoStatus);
}