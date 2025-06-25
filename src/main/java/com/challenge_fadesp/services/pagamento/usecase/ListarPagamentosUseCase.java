package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.FiltrarPagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;

import java.util.List;

public interface ListarPagamentosUseCase {
  List<PagamentoResponseDTO> listarTodos();

  List<PagamentoResponseDTO> filtrar(FiltrarPagamentoRequestDTO requestDTO);
}