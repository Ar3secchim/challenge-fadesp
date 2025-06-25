package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.utils.mapper.PagamentoMapper;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.BuscarPagamentoPorIdUseCase;
import org.springframework.stereotype.Service;
import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toResponseDTO;

@Service
public class BuscarPagamentoPorIdUseCaseImpl implements BuscarPagamentoPorIdUseCase {

  private final PagamentoRepository pagamentoRepository;

  public BuscarPagamentoPorIdUseCaseImpl(PagamentoRepository pagamentoRepository) {
    this.pagamentoRepository = pagamentoRepository;
  }

  @Override
  public PagamentoResponseDTO execute(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));
    return toResponseDTO(pagamento);
  }
}
