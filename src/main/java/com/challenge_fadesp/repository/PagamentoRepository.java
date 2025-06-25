package com.challenge_fadesp.repository;

import com.challenge_fadesp.domain.entity.Pagamento;
import com.challenge_fadesp.domain.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {
  List<Pagamento> findByCodigoDebito(Integer codigoDebito);

  List<Pagamento> findByIdentificadorPagamento(String identificadorPagamento);

  List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
}
