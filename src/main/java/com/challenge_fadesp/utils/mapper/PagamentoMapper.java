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
      .codigoDebito(requestDTO.codigoDebito())
      .identificadorPagamento(requestDTO.identificadorPagamento())
      .metodoPagamento(
        MetodoPagamento.valueOf(requestDTO.metodoPagamento().toString().toUpperCase())
      )
      .statusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO)
      .numeroCartao(requestDTO.numeroCartao())
      .valorPagamento(requestDTO.valorPagamento())
      .build();
  }

  public static PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
    return  new  PagamentoResponseDTO(
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
}
