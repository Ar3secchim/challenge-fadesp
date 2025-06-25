// CriarPagamentoUseCase.java
package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;

public interface CriarPagamentoUseCase {
  PagamentoResponseDTO execute(PagamentoRequestDTO requestDTO);
}
