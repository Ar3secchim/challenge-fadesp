package com.challenge_fadesp.dtos;

import com.challenge_fadesp.domain.enums.StatusPagamento;
import lombok.*;

public record FiltrarPagamentoRequestDTO(
    Integer codigoDebito,
    String identificadorPagamento,
    StatusPagamento statusPagamento
) {}