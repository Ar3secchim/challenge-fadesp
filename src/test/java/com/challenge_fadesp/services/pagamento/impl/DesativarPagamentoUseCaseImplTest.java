package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.utils.mapper.PagamentoMapper;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DesativarPagamentoUseCaseImplTest {
  @Mock
  private PagamentoRepository pagamentoRepository;

  @InjectMocks
  private DesativarPagamentoUseCaseImpl desativarPagamentoUseCase;

  private Pagamento pagamento;
  private PagamentoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    pagamento = new Pagamento();
    pagamento.setId(1L);
    pagamento.setAtivo(true);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

    responseDTO = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );
  }

  @Test
  void deveDesativarPagamentoComSucesso() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);

    PagamentoResponseDTO resultado = desativarPagamentoUseCase.execute(1L);

    assertNotNull(resultado);
    assertFalse(pagamento.getAtivo());
    verify(pagamentoRepository).save(pagamento);
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(PagamentoNaoEncontradoException.class, () ->
      desativarPagamentoUseCase.execute(1L));
  }

  @Test
  void deveLancarExcecaoQuandoStatusNaoPermitido() {
    pagamento.setStatusPagamento(StatusPagamento.PROCESSADO_SUCESSO);
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));

    assertThrows(OperacaoInvalidaException.class, () ->
      desativarPagamentoUseCase.execute(1L));
  }

}