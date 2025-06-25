package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.DesativarPagamentoUseCase;
import org.springframework.stereotype.Service;

@Service
public class DesativarPagamentoUseCaseImpl implements DesativarPagamentoUseCase {
  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public DesativarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }

  @Override
  public PagamentoResponseDTO execute(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

    if (pagamento.getStatusPagamento() != StatusPagamento.PENDENTE_PROCESSAMENTO) {
      throw new OperacaoInvalidaException("Apenas pagamentos pendentes podem ser desativados");
    }

    pagamento.setAtivo(false);
    return pagamentoMapper.toResponseDTO(pagamentoRepository.save(pagamento));
  }
}
