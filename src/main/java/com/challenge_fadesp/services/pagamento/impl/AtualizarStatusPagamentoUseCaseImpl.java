package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.exception.pagamentos.StatusInvalidoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.AtualizarStatusPagamentoUseCase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AtualizarStatusPagamentoUseCaseImpl implements AtualizarStatusPagamentoUseCase {
  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public AtualizarStatusPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }

  @Override
  public PagamentoResponseDTO execute(Long id, StatusPagamento novoStatus) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

    if (!pagamento.getAtivo()) {
      throw new OperacaoInvalidaException("Pagamentos inativos não podem ter seu status alterado");
    }

    if (!pagamento.getStatusPagamento().podeTransicionarPara(novoStatus)) {
      throw new StatusInvalidoException("Transição não permitida.");
    }

    pagamento.setStatusPagamento(novoStatus);
    return pagamentoMapper.toResponseDTO(pagamentoRepository.save(pagamento));
  }
}
