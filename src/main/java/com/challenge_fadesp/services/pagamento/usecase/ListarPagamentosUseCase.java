package com.challenge_fadesp.services.pagamento.usecase;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.model.enums.StatusPagamento;

import java.util.List;

public interface ListarPagamentosUseCase {
  List<PagamentoResponseDTO> listarTodos();

  List<PagamentoResponseDTO> filtrar(Integer codigoDebito, String identificadorPagamento, StatusPagamento status);
}