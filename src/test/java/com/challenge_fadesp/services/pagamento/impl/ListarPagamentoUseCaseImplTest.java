package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.FiltrarPagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPagamentoUseCaseImplTest {

  @Mock
  private PagamentoRepository pagamentoRepository;

  @Mock
  private PagamentoMapper pagamentoMapper;

  @InjectMocks
  private ListarPagamentoUseCaseImpl listarPagamentoUseCase;

  private Pagamento pagamento;
  private PagamentoResponseDTO responseDTO;

  @BeforeEach
  void setUp() {
    pagamento = new Pagamento();
    pagamento.setId(1L);

    responseDTO = new PagamentoResponseDTO();
  }

  @Test
  void deveListarTodosPagamentos() {
    when(pagamentoRepository.findAll()).thenReturn(Collections.singletonList(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.listarTodos();

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll();
    verify(pagamentoMapper).toResponseDTO(pagamento);
  }

  @Test
  void deveFiltrarPagamentosComCamposPreenchidos() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();
    filtro.setCodigoDebito(123);
    filtro.setIdentificadorPagamento("12345678900");
    filtro.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll(any(Specification.class));
    verify(pagamentoMapper).toResponseDTO(pagamento);
  }

  @Test
  void deveRetornarTodosQuandoFiltroVazio() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();

    when(pagamentoRepository.findAll()).thenReturn(List.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
    verify(pagamentoRepository).findAll();
    verify(pagamentoMapper).toResponseDTO(pagamento);
  }

  @Test
  void deveRetornarListaVaziaQuandoNenhumEncontrado() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();
    filtro.setCodigoDebito(999);

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertTrue(resultado.isEmpty());
    verify(pagamentoRepository).findAll(any(Specification.class));
  }

  @Test
  void deveFiltrarComApenasCodigoDebito() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();
    filtro.setCodigoDebito(123);

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }

  @Test
  void deveFiltrarComApenasIdentificador() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();
    filtro.setIdentificadorPagamento("12345678900");

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }

  @Test
  void deveFiltrarComApenasStatus() {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO();
    filtro.setStatusPagamento(StatusPagamento.PROCESSADO_SUCESSO);

    when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));
    when(pagamentoMapper.toResponseDTO(pagamento)).thenReturn(responseDTO);

    List<PagamentoResponseDTO> resultado = listarPagamentoUseCase.filtrar(filtro);

    assertEquals(1, resultado.size());
  }
}
