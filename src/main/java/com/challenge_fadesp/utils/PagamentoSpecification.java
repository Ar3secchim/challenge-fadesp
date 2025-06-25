package com.challenge_fadesp.utils;

import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import org.springframework.data.jpa.domain.Specification;


public class PagamentoSpecification {
  // Esta classe fornece especificações para filtrar entidades Pagamento com base em critérios.
  public static Specification<Pagamento> comCodigoDebito(Integer codigoDebito) {
    return (root, query, criteriaBuilder) ->
      codigoDebito == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.equal(root.get("codigoDebito"), codigoDebito);
  }

  public static Specification<Pagamento> comIdentificador(String identificadorPagamento) {
    return (root, query, criteriaBuilder) ->
      identificadorPagamento == null || identificadorPagamento.isEmpty() ? criteriaBuilder.conjunction()
        : criteriaBuilder.equal(root.get("identificadorPagamento"), identificadorPagamento);
  }

  public static Specification<Pagamento> comStatus(StatusPagamento status) {
    return (root, query, criteriaBuilder) ->
      status == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.equal(root.get("statusPagamento"), status);
  }
}