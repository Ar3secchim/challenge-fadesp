package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.BuscarPagamentoPorIdUseCase;


public class BuscarPagamentoPorIdUseCaseImpl implements BuscarPagamentoPorIdUseCase {

  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public BuscarPagamentoPorIdUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }

  @Override
  public PagamentoResponseDTO execute(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));
    return pagamentoMapper.toResponseDTO(pagamento);
  }
}
