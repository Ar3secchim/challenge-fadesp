// CriarPagamentoUseCase.java
package com.challenge_fadesp.usecase.pagamento;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;

public interface CriarPagamentoUseCase {
  PagamentoResponseDTO execute(PagamentoRequestDTO requestDTO);
}
