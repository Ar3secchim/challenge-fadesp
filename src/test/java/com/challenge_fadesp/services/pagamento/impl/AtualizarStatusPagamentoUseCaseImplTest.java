package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.exception.pagamentos.StatusInvalidoException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPagamentoUseCaseImplTest {
  @Mock
  private PagamentoRepository pagamentoRepository;

  @InjectMocks
  private AtualizarStatusPagamentoUseCaseImpl atualizarStatusPagamentoUseCase;

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
  void deveAtualizarStatusComSucesso() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);

    PagamentoResponseDTO resultado = atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO);

    assertNotNull(resultado);
    assertEquals(StatusPagamento.PROCESSADO_SUCESSO, pagamento.getStatusPagamento());

    verify(pagamentoRepository).save(pagamento);
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(PagamentoNaoEncontradoException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO));
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoInativo() {
    pagamento.setAtivo(false);
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));

    assertThrows(OperacaoInvalidaException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO));
  }

  @Test
  void deveLancarExcecaoQuandoTransicaoInvalida() {
    pagamento.setAtivo(true);
    pagamento.setStatusPagamento(StatusPagamento.PROCESSADO_SUCESSO); // Não pode ir para FALHA

    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));

    assertThrows(StatusInvalidoException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_FALHA));
  }
}