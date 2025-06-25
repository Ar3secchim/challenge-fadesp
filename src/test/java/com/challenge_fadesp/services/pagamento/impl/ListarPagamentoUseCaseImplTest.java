package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.dtos.FiltrarPagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
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
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPagamentoUseCaseImplTest {

  @Mock
  private PagamentoRepository pagamentoRepository;

  @InjectMocks
  private ListarPagamentoUseCaseImpl listarPagamentoUseCase;

  private Pagamento pagamento;
  private PagamentoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    pagamento = new Pagamento();
    pagamento.setId(1L);

    responseDTO = new PagamentoResponseDTO(
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
  void deveListarTodosPagamentos() {
    when(pagamentoRepository.findAll()).thenReturn(Collections.singletonList(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.listarTodos();

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll();
  }

  @Test
  void deveFiltrarPagamentosComCamposPreenchidos() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );


    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll(any(Specification.class));
  }

  @Test
  void deveRetornarTodosQuandoFiltroVazio() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );

    when(pagamentoRepository.findAll()).thenReturn(List.of(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll();
  }

  @Test
  void deveRetornarListaVaziaQuandoNenhumEncontrado() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertTrue(resultado.isEmpty());
    verify(pagamentoRepository).findAll(any(Specification.class));
  }

  @Test
  void deveFiltrarComApenasCodigoDebito() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }

  @Test
  void deveFiltrarComApenasIdentificador() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );


    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }

  @Test
  void deveFiltrarComApenasStatus() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      1,
      "1234567891012",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }
}
