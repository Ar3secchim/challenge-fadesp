package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.utils.mapper.PagamentoMapper;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.CriarPagamentoUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toEntity;
import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toResponseDTO;

@Service
public class CriarPagamentoUseCaseImpl implements CriarPagamentoUseCase {

  private final PagamentoRepository pagamentoRepository;

  public CriarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository) {
    this.pagamentoRepository = pagamentoRepository;
  }

  @Override
  public PagamentoResponseDTO execute(PagamentoRequestDTO requestDTO) {
    if (requestDTO == null) {
      throw new OperacaoInvalidaException("Pagamento não pode ser nulo");
    }

    if (requestDTO.valorPagamento() == null || requestDTO.valorPagamento().compareTo(BigDecimal.ZERO) <= 0) {
      throw new OperacaoInvalidaException("Valor do pagamento deve ser maior que zero");
    }

    if (requestDTO.metodoPagamento() == null) {
      throw new OperacaoInvalidaException("Método de pagamento não pode ser nulo ou vazio");
    }

    if (requestDTO.identificadorPagamento() == null) {
      throw new OperacaoInvalidaException("Identificador do pagamento não pode ser nulo ou vazio");
    }

    // Verifica se o identificador de pagamento é um número de 11 ou 14 dígitos
    if (!(requestDTO.identificadorPagamento().matches("\\d{11}") || requestDTO.identificadorPagamento().matches("\\d{14}"))) {
      throw new OperacaoInvalidaException("Identificador do pagamento inválido. Deve conter 11 ou 14 dígitos.");
    }

    if (requestDTO.metodoPagamento() == MetodoPagamento.CARTAO_DEBITO || requestDTO.metodoPagamento() == MetodoPagamento.CARTAO_CREDITO) {
      if (requestDTO.numeroCartao() == null || requestDTO.numeroCartao().isBlank()) {
        throw new OperacaoInvalidaException("Número do cartão não pode ser nulo ou vazio para pagamentos com cartão");
      }
    }

    Pagamento pagamento = toEntity(requestDTO);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);
    pagamento.setAtivo(true);

    Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
    return toResponseDTO(pagamentoSalvo);
  }
}