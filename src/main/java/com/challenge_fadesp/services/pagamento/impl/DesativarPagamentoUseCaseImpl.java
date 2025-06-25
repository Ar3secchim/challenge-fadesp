package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.utils.mapper.PagamentoMapper;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.DesativarPagamentoUseCase;
import org.springframework.stereotype.Service;

import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toResponseDTO;

@Service
public class DesativarPagamentoUseCaseImpl implements DesativarPagamentoUseCase {
  private final PagamentoRepository pagamentoRepository;

  public DesativarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository) {
    this.pagamentoRepository = pagamentoRepository;
  }

  @Override
  public PagamentoResponseDTO execute(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

    if (pagamento.getStatusPagamento() != StatusPagamento.PENDENTE_PROCESSAMENTO) {
      throw new OperacaoInvalidaException("Apenas pagamentos pendentes podem ser desativados");
    }

    pagamento.setAtivo(false);
    return toResponseDTO(pagamentoRepository.save(pagamento));
  }
}
