package com.challenge_fadesp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.challenge_fadesp.dtos.FiltrarPagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.dtos.StatusPagamentoRequestDTO;
import com.challenge_fadesp.model.enums.StatusPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.challenge_fadesp.services.pagamento.usecase.*;

import java.util.List;

@RestController
@RequestMapping("api/pagamentos")
public class PagamentoController {

  private final ListarPagamentosUseCase listarPagamentosUseCase;
  private final BuscarPagamentoPorIdUseCase buscarPagamentoUseCase;
  private final AtualizarStatusPagamentoUseCase atualizarStatusUseCase;
  private final CriarPagamentoUseCase criarPagamentoUseCase;
  private final DesativarPagamentoUseCase desativarPagamentoUseCase;

  @Autowired
  public PagamentoController(
    CriarPagamentoUseCase criarPagamentoUseCase,
    ListarPagamentosUseCase listarPagamentosUseCase,
    BuscarPagamentoPorIdUseCase buscarPagamentoUseCase,
    AtualizarStatusPagamentoUseCase atualizarStatusUseCase,
    DesativarPagamentoUseCase desativarPagamentoUseCase) {
    this.criarPagamentoUseCase = criarPagamentoUseCase;
    this.listarPagamentosUseCase = listarPagamentosUseCase;
    this.buscarPagamentoUseCase = buscarPagamentoUseCase;
    this.atualizarStatusUseCase = atualizarStatusUseCase;
    this.desativarPagamentoUseCase = desativarPagamentoUseCase;
  }

  @PostMapping
  @Operation(summary = "Criar um novo pagamento", description = "Cria um pagamento e retorna o recurso criado.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
  })
  public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
    PagamentoResponseDTO responseDTO = criarPagamentoUseCase.execute(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PostMapping("/filtrar-pagamentos")
  @Operation(summary = "Filtrar pagamentos", description = "Filtra pagamentos de acordo com os critérios fornecidos.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pagamentos filtrados retornados com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
  })
  public ResponseEntity<List<PagamentoResponseDTO>> filtrarPagamentos(@RequestBody FiltrarPagamentoRequestDTO requestDTO) {
    List<PagamentoResponseDTO> pagamentos = listarPagamentosUseCase.filtrar(requestDTO);
    return ResponseEntity.ok(pagamentos);
  }

  @GetMapping()
  @Operation(summary = "Listar todos os pagamentos", description = "Retorna uma lista de todos os pagamentos cadastrados.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso")
  })
  public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
    List<PagamentoResponseDTO> listaPagamentos = listarPagamentosUseCase.listarTodos();
    return ResponseEntity.ok(listaPagamentos);
  }


  @GetMapping("/{id}")
  @Operation(summary = "Buscar pagamento por ID", description = "Busca um pagamento pelo seu identificador único.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pagamento encontrado e retornado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
  })
  public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = buscarPagamentoUseCase.execute(id);
    return ResponseEntity.ok(pagamento);
  }

  @PutMapping("/{id}/status")
  @Operation(summary = "Atualizar status do pagamento", description = "Atualiza o status de um pagamento existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Status do pagamento atualizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
  })
  public ResponseEntity<PagamentoResponseDTO> atualizarStatusPagamento(
    @PathVariable Long id,
    @RequestBody StatusPagamentoRequestDTO request) {
    StatusPagamento novoStatus = StatusPagamento.valueOf(request.getStatusPagamento().toUpperCase());
    PagamentoResponseDTO pagamento = atualizarStatusUseCase.execute(id, novoStatus);
    return ResponseEntity.ok(pagamento);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Desativar pagamento", description = "Desativa um pagamento existente pelo seu ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pagamento desativado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
  })
  public ResponseEntity<PagamentoResponseDTO> desativarPagamento(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = desativarPagamentoUseCase.execute(id);
    return ResponseEntity.ok(pagamento);
  }
}
