package com.challenge_fadesp.dtos;

import com.challenge_fadesp.domain.enums.MetodoPagamento;
import lombok.*;

import java.math.BigDecimal;

public record PagamentoRequestDTO (
  Integer codigoDebito,
  String identificadorPagamento,
  MetodoPagamento metodoPagamento,
  String numeroCartao,
  BigDecimal valorPagamento
) { }
