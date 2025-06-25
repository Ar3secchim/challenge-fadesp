package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.exception.pagamentos.StatusInvalidoException;

import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.AtualizarStatusPagamentoUseCase;
import org.springframework.stereotype.Service;

import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toResponseDTO;

@Service
public class AtualizarStatusPagamentoUseCaseImpl implements AtualizarStatusPagamentoUseCase {
  private final PagamentoRepository pagamentoRepository;

  public AtualizarStatusPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository) {
    this.pagamentoRepository = pagamentoRepository;
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
    return toResponseDTO(pagamentoRepository.save(pagamento));
  }
}
