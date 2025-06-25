package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.MetodoPagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarPagamentoUseCaseImplTest {

  @Mock
  private PagamentoRepository pagamentoRepository;

  @Mock
  private PagamentoMapper pagamentoMapper;

  @InjectMocks
  private CriarPagamentoUseCaseImpl criarPagamentoUseCase;

  private PagamentoRequestDTO request;
  private Pagamento pagamento;
  private PagamentoResponseDTO response;

  @BeforeEach
  void setUp() {
    request = new PagamentoRequestDTO();
    request.setCodigoDebito(123);
    request.setIdentificadorPagamento("12345678900");
    request.setMetodoPagamento(MetodoPagamento.PIX);
    request.setValorPagamento(BigDecimal.valueOf(100));

    pagamento = new Pagamento();
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

    response = new PagamentoResponseDTO();
  }

  @Test
  void deveCriarPagamentoComSucesso() {
    when(pagamentoMapper.toEntity(request)).thenReturn(pagamento);
    when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(response);

    PagamentoResponseDTO resultado = criarPagamentoUseCase.execute(request);

    assertNotNull(resultado);
    verify(pagamentoRepository).save(pagamento);
    verify(pagamentoMapper).toResponseDTO(pagamento);
  }

  @Test
  void deveLancarExcecaoSeRequestForNulo() {
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(null));
  }

  @Test
  void deveLancarExcecaoSeValorZeroOuNulo() {
    request.setValorPagamento(BigDecimal.ZERO);
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeMetodoPagamentoForNulo() {
    request.setMetodoPagamento(null);
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeIdentificadorInvalido() {
    request.setIdentificadorPagamento("123");
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeCartaoNaoInformadoParaMetodoComCartao() {
    request.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
    request.setNumeroCartao(null);
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }
}
