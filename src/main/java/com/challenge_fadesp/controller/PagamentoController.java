package com.challenge_fadesp.controller;

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
  public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
    PagamentoResponseDTO responseDTO = criarPagamentoUseCase.execute(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PostMapping("/filtrar-pagamentos")
  public ResponseEntity<List<PagamentoResponseDTO>> filtrarPagamentos(@RequestBody FiltrarPagamentoRequestDTO requestDTO) {
    List<PagamentoResponseDTO> pagamentos = listarPagamentosUseCase.filtrar(requestDTO);
    return ResponseEntity.ok(pagamentos);
  }

  @GetMapping()
  public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
    List<PagamentoResponseDTO> listaPagamentos = listarPagamentosUseCase.listarTodos();
    return ResponseEntity.ok(listaPagamentos);
  }


  @GetMapping("/{id}")
  public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = buscarPagamentoUseCase.execute(id);
    return ResponseEntity.ok(pagamento);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<PagamentoResponseDTO> atualizarStatusPagamento(
    @PathVariable Long id,
    @RequestBody StatusPagamentoRequestDTO request) {
    StatusPagamento novoStatus = StatusPagamento.valueOf(request.getStatusPagamento().toUpperCase());
    PagamentoResponseDTO pagamento = atualizarStatusUseCase.execute(id, novoStatus);
    return ResponseEntity.ok(pagamento);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PagamentoResponseDTO> desativarPagamento(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = desativarPagamentoUseCase.execute(id);
    return ResponseEntity.ok(pagamento);
  }
}
