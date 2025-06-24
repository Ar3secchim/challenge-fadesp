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

@Component
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

    switch (pagamento.getStatusPagamento()) {
      case PENDENTE_PROCESSAMENTO:
        if (novoStatus == StatusPagamento.PROCESSADO_SUCESSO ||
          novoStatus == StatusPagamento.PROCESSADO_FALHA) {
          pagamento.setStatusPagamento(novoStatus);
        } else {
          throw new StatusInvalidoException("Um pagamento pendente só pode ser alterado para processado com sucesso ou falha");
        }
        break;
      case PROCESSADO_SUCESSO:
        throw new StatusInvalidoException("Um pagamento processado com sucesso não pode ter seu status alterado");
      case PROCESSADO_FALHA:
        if (novoStatus == StatusPagamento.PENDENTE_PROCESSAMENTO) {
          pagamento.setStatusPagamento(novoStatus);
        } else {
          throw new StatusInvalidoException("Um pagamento processado com falha só pode ser alterado para pendente");
        }
        break;
    }

    return pagamentoMapper.toResponseDTO(pagamentoRepository.save(pagamento));
  }
}
