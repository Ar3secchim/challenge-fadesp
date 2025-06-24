package com.challenge_fadesp.usecase.pagamento;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;

public interface DesativarPagamentoUseCase {
  PagamentoResponseDTO execute(Long id);
}