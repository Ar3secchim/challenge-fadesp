package com.challenge_fadesp.mapper;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.model.MetodoPagamento;
import com.challenge_fadesp.model.Pagamento;
import com.challenge_fadesp.model.StatusPagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {
  public Pagamento toEntity(PagamentoRequestDTO requestDTO) {
    return Pagamento.builder()
      .codigoDebito(requestDTO.getCodigoDebito())
      .identificadorPagamento(requestDTO.getIdentificadorPagamento())
      .metodoPagamento(
        MetodoPagamento.valueOf(requestDTO.getMetodoPagamento().toString().toUpperCase())
      )
      .statusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO)
      .numeroCartao(requestDTO.getNumeroCartao())
      .valorPagamento(requestDTO.getValorPagamento())
      .build();
  }

  public PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
    return PagamentoResponseDTO.builder()
      .id(pagamento.getId())
      .codigoDebito(pagamento.getCodigoDebito())
      .identificadorPagamento(pagamento.getIdentificadorPagamento())
      .metodoPagamento(pagamento.getMetodoPagamento())
      .numeroCartao(pagamento.getNumeroCartao())
      .valorPagamento(pagamento.getValorPagamento())
      .statusPagamento(pagamento.getStatusPagamento())
      .ativo(pagamento.getAtivo())
      .build();
  }
}
