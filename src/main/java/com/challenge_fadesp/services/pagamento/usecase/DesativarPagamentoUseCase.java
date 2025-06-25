package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;

public interface DesativarPagamentoUseCase {
  PagamentoResponseDTO execute(Long id);
}