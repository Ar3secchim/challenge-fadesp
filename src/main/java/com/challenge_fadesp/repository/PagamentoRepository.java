package com.challenge_fadesp.repository;

import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
  List<Pagamento> findByCodigoDebito(Integer codigoDebito);

  List<Pagamento> findByIdentificadorPagamento(String identificadorPagamento);

  List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
}
