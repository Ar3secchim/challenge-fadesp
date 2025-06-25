package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static com.challenge_fadesp.utils.mapper.PagamentoMapper.toResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPagamentoPorIdUseCaseImplTest {
  @Mock
  private PagamentoRepository pagamentoRepository;


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
    pagamento.setDataAtualizacao(LocalDateTime.now());

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
  void deveBuscarPagamentoPorIdComSucesso() {
    when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));

    PagamentoResponseDTO resultado = BuscarPagamentoPorIdUseCase.execute(1L);

    assertNotNull(resultado);
    assertEquals(responseDTO.id(), resultado.id());
    assertEquals(responseDTO.statusPagamento(), resultado.statusPagamento());

    verify(pagamentoRepository).findById(1L);
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