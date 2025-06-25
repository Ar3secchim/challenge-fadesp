package com.challenge_fadesp.dtos;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;

public record PagamentoResponseDTO (
   Long id,
   Integer codigoDebito,
   String identificadorPagamento,
   MetodoPagamento metodoPagamento,
   String numeroCartao,
   BigDecimal valorPagamento,
   StatusPagamento statusPagamento,
   Boolean ativo
){}