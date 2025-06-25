package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.utils.mapper.PagamentoMapper;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
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


  @InjectMocks
  private CriarPagamentoUseCaseImpl criarPagamentoUseCase;

  private PagamentoRequestDTO request;
  private Pagamento pagamento;
  private PagamentoResponseDTO response;

  @BeforeEach
  void setUp() {
    request = new PagamentoRequestDTO(
      1,
      "1234567890123456",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00)
    );


    pagamento = new Pagamento();
    pagamento.setCodigoDebito(123);
    pagamento.setIdentificadorPagamento("12345678900");
    pagamento.setMetodoPagamento(MetodoPagamento.PIX);
    pagamento.setValorPagamento(BigDecimal.valueOf(100));
    pagamento.setNumeroCartao(null);
    pagamento.setAtivo(true);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

    response = new PagamentoResponseDTO(
      pagamento.getId(),
      pagamento.getCodigoDebito(),
      pagamento.getIdentificadorPagamento(),
      pagamento.getMetodoPagamento(),
      pagamento.getNumeroCartao(),
      pagamento.getValorPagamento(),
      pagamento.getStatusPagamento(),
      pagamento.getAtivo()
    );
  }

  @Test
  void deveCriarPagamentoComSucesso() {
    when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);

    PagamentoResponseDTO resultado = criarPagamentoUseCase.execute(request);

    assertNotNull(resultado);
    verify(pagamentoRepository).save(pagamento);
  }

  @Test
  void deveLancarExcecaoSeRequestForNulo() {
    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(null));
  }

  @Test
  void deveLancarExcecaoSeValorZeroOuNulo() {
    request = new PagamentoRequestDTO(
      1,
      "1234567890123456",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.ZERO
    );

    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeMetodoPagamentoForNulo() {
    request = new PagamentoRequestDTO(
      1,
      "1234567890123456",
      null,
      "1234567890123456",
      BigDecimal.ZERO
    );

    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeIdentificadorInvalido() {
    request = new PagamentoRequestDTO(
      1,
      "a",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.ZERO
    );

    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }

  @Test
  void deveLancarExcecaoSeCartaoNaoInformadoParaMetodoComCartao() {
    request = new PagamentoRequestDTO(
      1,
      "1234567890123456",
      MetodoPagamento.CARTAO_CREDITO,
      null,
      BigDecimal.ZERO
    );

    assertThrows(OperacaoInvalidaException.class, () -> criarPagamentoUseCase.execute(request));
  }
}
