package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.ListarPagamentosUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class ListarPagamentoUseCaseImpl implements ListarPagamentosUseCase {
  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public ListarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }


  @Override
  public List<PagamentoResponseDTO> listarTodos() {
    return pagamentoRepository.findAll().stream()
      .map(pagamentoMapper::toResponseDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<PagamentoResponseDTO> filtrar(Integer codigoDebito, String identificadorPagamento, StatusPagamento status) {
    Integer codigoDebito, String identificadorPagamento, StatusPagamento statusPagamento){
      List<Pagamento> pagamentos;

      if (codigoDebito != null) {
        pagamentos = pagamentoRepository.findByCodigoDebito(codigoDebito).stream().collect(Collectors.toList());
      } else if (identificadorPagamento != null && !identificadorPagamento.isEmpty()) {
        pagamentos = pagamentoRepository.findByIdentificadorPagamento(identificadorPagamento);
      } else if (statusPagamento != null) {
        pagamentos = pagamentoRepository.findByStatusPagamento(statusPagamento);
      } else {
        pagamentos = pagamentoRepository.findAll();
      }

      return pagamentos.stream()
        .map(pagamentoMapper::toResponseDTO)
        .collect(Collectors.toList());
    }
  }
