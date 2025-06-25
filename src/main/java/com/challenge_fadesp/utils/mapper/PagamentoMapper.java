package com.challenge_fadesp.utils.mapper;

import com.challenge_fadesp.dtos.PagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import org.springframework.stereotype.Component;

public class PagamentoMapper {
  public static Pagamento toEntity(PagamentoRequestDTO requestDTO) {
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

  public static PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
    return  PagamentoResponseDTO.builder()
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
