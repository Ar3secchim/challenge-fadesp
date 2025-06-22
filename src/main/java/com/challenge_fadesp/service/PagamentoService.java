package com.challenge_fadesp.service;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.MetodoPagamento;
import com.challenge_fadesp.model.Pagamento;
import com.challenge_fadesp.model.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {
  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  @Autowired
  public PagamentoService(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }

  public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO requestDTO) {
    Pagamento pagamento = pagamentoMapper.toEntity(requestDTO);
    pagamento.setStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);
    pagamento.setAtivo(true);
    pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);

    Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
    return pagamentoMapper.toResponseDTO(pagamentoSalvo);
  }

  public List<PagamentoResponseDTO> listarPagamentos() {
    return pagamentoRepository.findAll().stream()
      .map(pagamentoMapper::toResponseDTO)
      .collect(Collectors.toList());
  }

  public List<PagamentoResponseDTO> listarPagamentosFiltrados(
    Integer codigoDebito, String identificadorPagamento, StatusPagamento statusPagamento) {
    List<Pagamento> pagamentos;

    pagamentos = getPagamentos(codigoDebito, identificadorPagamento, statusPagamento);

    return pagamentos.stream()
      .map(pagamentoMapper::toResponseDTO)
      .collect(Collectors.toList());
  }

  public PagamentoResponseDTO buscarPorId(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado com ID: " + id));
    return pagamentoMapper.toResponseDTO(pagamento);
  }

  public PagamentoResponseDTO atualizarStatus(Long id, StatusPagamento novoStatus) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado com ID: " + id));

    switch (pagamento.getStatusPagamento()) {
      case PENDENTE_PROCESSAMENTO:
        if (novoStatus == StatusPagamento.PROCESSADO_SUCESSO ||
          novoStatus == StatusPagamento.PROCESSADO_FALHA) {
          pagamento.setStatusPagamento(novoStatus);
        } else {
          throw new IllegalStateException("Um pagamento pendente só pode ser alterado para processado com sucesso ou falha");
        }
        break;
      case PROCESSADO_SUCESSO:
        throw new IllegalStateException("Um pagamento processado com sucesso não pode ter seu status alterado");
      case PROCESSADO_FALHA:
        if (novoStatus == StatusPagamento.PENDENTE_PROCESSAMENTO) {
          pagamento.setStatusPagamento(novoStatus);
        } else {
          throw new IllegalStateException("Um pagamento processado com falha só pode ser alterado para pendente");
        }
        break;
    }

    return pagamentoMapper.toResponseDTO(pagamentoRepository.save(pagamento));
  }

  public PagamentoResponseDTO desativarPagamento(Long id) {
    Pagamento pagamento = pagamentoRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado com ID: " + id));

    if (pagamento.getStatusPagamento() != StatusPagamento.PENDENTE_PROCESSAMENTO) {
      throw new IllegalStateException("Apenas pagamentos pendentes podem ser desativados");
    }

    pagamento.setAtivo(false);
    return pagamentoMapper.toResponseDTO(pagamentoRepository.save(pagamento));
  }

  private List<Pagamento> getPagamentos(Integer codigoDebito, String identificadorPagamento, StatusPagamento statusPagamento) {
    List<Pagamento> pagamentos;

    if (codigoDebito != null) {
      pagamentos = pagamentoRepository.findByCodigoDebito(codigoDebito).stream().collect(Collectors.toList());
    } else if (identificadorPagamento != null && !identificadorPagamento.isEmpty()) {
      pagamentos = pagamentoRepository.findByIdentificadorPagamento(identificadorPagamento);
    } else if (statusPagamento != null) {
      pagamentos = pagamentoRepository.findByStatusPagamento(statusPagamento);
    } else {
      pagamentos = pagamentoRepository.findAll();
    }
    return pagamentos;
  }
}
