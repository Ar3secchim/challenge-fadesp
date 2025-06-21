package com.challenge_fadesp.controller;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.model.StatusPagamento;
import com.challenge_fadesp.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pagamentos")
public class PagamentoController {

  private final PagamentoService pagamentoService;

  @Autowired
  public PagamentoController(PagamentoService pagamentoService) {
    this.pagamentoService = pagamentoService;
  }

  @PostMapping
  public ResponseEntity<PagamentoResponseDTO> criarPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
    PagamentoResponseDTO responseDTO = pagamentoService.criarPagamento(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping
  public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos(
    @RequestParam(required = false) Integer codigoDebito,
    @RequestParam(required = false) String identificadorPagamento,
    @RequestParam(required = false) StatusPagamento statusPagamento) {

    List<PagamentoResponseDTO> pagamentos = pagamentoService.listarPagamentosFiltrados(
      codigoDebito, identificadorPagamento, statusPagamento);
    return ResponseEntity.ok(pagamentos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = pagamentoService.buscarPorId(id);
    return ResponseEntity.ok(pagamento);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<PagamentoResponseDTO> atualizarStatusPagamento(
    @PathVariable Long id,
    @RequestBody AtualizacaoStatusDTO atualizacaoStatusDTO) {
    PagamentoResponseDTO pagamento = pagamentoService.atualizarStatus(id, atualizacaoStatusDTO.getStatusPagamento());
    return ResponseEntity.ok(pagamento);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<PagamentoResponseDTO> desativarPagamento(@PathVariable Long id) {
    PagamentoResponseDTO pagamento = pagamentoService.desativarPagamento(id);
    return ResponseEntity.ok(pagamento);
  }
}
