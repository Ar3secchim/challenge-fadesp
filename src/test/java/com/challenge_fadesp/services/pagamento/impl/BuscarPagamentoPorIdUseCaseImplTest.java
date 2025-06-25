package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.PagamentoNaoEncontradoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPagamentoPorIdUseCaseImplTest {
  @Mock
  private PagamentoRepository pagamentoRepository;

  @Mock
  private PagamentoMapper pagamentoMapper;

  @InjectMocks
  private BuscarPagamentoPorIdUseCaseImpl BuscarPagamentoPorIdUseCase;

  private Pagamento pagamento;
  private PagamentoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    pagamento = new Pagamento();
    pagamento.setId(1L);
    pagamento.setAtivo(true);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

    responseDTO = new PagamentoResponseDTO();
  }

  @Test
  void deveBuscarPagamentoPorIdComSucesso() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    PagamentoResponseDTO resultado = BuscarPagamentoPorIdUseCase.execute(1L);

    assertNotNull(resultado);
    assertEquals(responseDTO, resultado);

    verify(pagamentoRepository).findById(1L);
    verify(pagamentoMapper).toResponseDTO(pagamento);
  }

  @Test
  void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(PagamentoNaoEncontradoException.class, () -> {
      BuscarPagamentoPorIdUseCase.execute(1L);
    });

    assertTrue(exception.getMessage().contains("Pagamento não encontrado"));
    verify(pagamentoRepository).findById(1L);
  }
}