package com.challenge_fadesp.controller;

import com.challenge_fadesp.dtos.*;
import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import com.challenge_fadesp.services.pagamento.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagamentoController.class)
@ExtendWith(SpringExtension.class)
class PagamentoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CriarPagamentoUseCase criarPagamentoUseCase;
  @MockBean
  private ListarPagamentosUseCase listarPagamentosUseCase;
  @MockBean
  private BuscarPagamentoPorIdUseCase buscarPagamentoUseCase;
  @MockBean
  private AtualizarStatusPagamentoUseCase atualizarStatusUseCase;
  @MockBean
  private DesativarPagamentoUseCase desativarPagamentoUseCase;


  @Test
  void deveCriarPagamentoComSucesso() throws Exception {
    PagamentoRequestDTO requestDTO = new PagamentoRequestDTO(
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00)
    );


    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );

    Mockito.when(criarPagamentoUseCase.execute(any())).thenReturn(response);

    mockMvc.perform(post("/api/pagamentos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDTO)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  void deveListarTodosPagamentos() throws Exception {
    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );

    Mockito.when(listarPagamentosUseCase.listarTodos()).thenReturn(List.of(response));

    mockMvc.perform(get("/api/pagamentos"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1L));
  }

  @Test
  void deveFiltrarPagamentosComSucesso() throws Exception {
    FiltrarPagamentoRequestDTO filtro = new FiltrarPagamentoRequestDTO(
      123,
      "123456789111",
      StatusPagamento.PENDENTE_PROCESSAMENTO
    );

    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );


    Mockito.when(listarPagamentosUseCase.filtrar(any())).thenReturn(List.of(response));

    mockMvc.perform(post("/api/pagamentos/filtrar-pagamentos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(filtro)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(1L));
  }

  @Test
  void deveBuscarPagamentoPorId() throws Exception {
    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );


    Mockito.when(buscarPagamentoUseCase.execute(1L)).thenReturn(response);

    mockMvc.perform(get("/api/pagamentos/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  void deveAtualizarStatusComSucesso() throws Exception {
    StatusPagamentoRequestDTO statusDTO = new StatusPagamentoRequestDTO(
      StatusPagamento.PROCESSADO_SUCESSO
    );

    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PROCESSADO_SUCESSO,
      true
    );


    Mockito.when(atualizarStatusUseCase.execute(1L, StatusPagamento.PROCESSADO_SUCESSO)).thenReturn(response);

    mockMvc.perform(put("/api/pagamentos/1/status")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(statusDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  void deveDesativarPagamento() throws Exception {
    PagamentoResponseDTO response = new PagamentoResponseDTO(
      1L,
      123,
      "123456789111",
      MetodoPagamento.PIX,
      "1234567890123456",
      BigDecimal.valueOf(150.00),
      StatusPagamento.PENDENTE_PROCESSAMENTO,
      true
    );

    Mockito.when(desativarPagamentoUseCase.execute(1L)).thenReturn(response);

    mockMvc.perform(put("/api/pagamentos/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1L));
  }
}
