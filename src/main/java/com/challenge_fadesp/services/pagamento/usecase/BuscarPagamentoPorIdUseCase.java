package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;

public interface BuscarPagamentoPorIdUseCase {
  PagamentoResponseDTO execute(Long id);
}