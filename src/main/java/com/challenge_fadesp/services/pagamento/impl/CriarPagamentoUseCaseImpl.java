package com.challenge_fadesp.usecase.impl;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.usecase.pagamento.CriarPagamentoUseCase;
import org.springframework.stereotype.Component;

@Component
public class CriarPagamentoUseCaseImpl implements CriarPagamentoUseCase {

  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public CriarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }

  @Override
  public PagamentoResponseDTO execute(PagamentoRequestDTO requestDTO) {
    Pagamento pagamento = pagamentoMapper.toEntity(requestDTO);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);
    pagamento.setAtivo(true);

    Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
    return pagamentoMapper.toResponseDTO(pagamentoSalvo);
  }
}