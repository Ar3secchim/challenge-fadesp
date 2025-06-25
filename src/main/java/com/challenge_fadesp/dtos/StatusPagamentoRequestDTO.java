package com.challenge_fadesp.dtos;

import com.challenge_fadesp.domain.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record StatusPagamentoRequestDTO (
   StatusPagamento statusPagamento
){}
