package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.exception.pagamentos.StatusInvalidoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtualizarStatusPagamentoUseCaseImplTest {
  @Mock
  private PagamentoRepository pagamentoRepository;
  @Mock
  private PagamentoMapper pagamentoMapper;
  @InjectMocks
  private AtualizarStatusPagamentoUseCaseImpl atualizarStatusPagamentoUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deveAtualizarStatusComSucesso() {
    Pagamento pagamento = mock(Pagamento.class);
    StatusPagamento statusPagamento = mock(StatusPagamento.class);
    when(pagamento.getAtivo()).thenReturn(true);
    when(pagamento.getStatusPagamento()).thenReturn(statusPagamento);
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    when(statusPagamento.podeTransicionarPara(StatusPagamento.PROCESSADO_SUCESSO)).thenReturn(true);
    when(pagamentoRepository.save(any())).thenReturn(pagamento);
    PagamentoResponseDTO responseDTO = new PagamentoResponseDTO();
    when(pagamentoMapper.toResponseDTO(any())).thenReturn(responseDTO);

    PagamentoResponseDTO result = atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO);
    assertEquals(responseDTO, result);
    verify(pagamento).setStatusPagamento(StatusPagamento.PROCESSADO_SUCESSO);
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(PagamentoNaoEncontradoException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO)
    );
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoInativo() {
    Pagamento pagamento = mock(Pagamento.class);
    when(pagamento.getAtivo()).thenReturn(false);
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    assertThrows(OperacaoInvalidaException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO)
    );
  }

  @Test
  void deveLancarExcecaoQuandoTransicaoInvalida() {
    Pagamento pagamento = mock(Pagamento.class);
    StatusPagamento statusPagamento = mock(StatusPagamento.class);
    when(pagamento.getAtivo()).thenReturn(true);
    when(pagamento.getStatusPagamento()).thenReturn(statusPagamento);
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    when(statusPagamento.podeTransicionarPara(StatusPagamento.PROCESSADO_FALHA)).thenReturn(false);
    assertThrows(StatusInvalidoException.class, () ->
      atualizarStatusPagamentoUseCase.execute(1L, StatusPagamento.PROCESSADO_FALHA)
    );
  }
}
