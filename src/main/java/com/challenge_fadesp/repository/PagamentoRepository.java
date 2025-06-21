package com.challenge_fadesp.repository;

import com.challenge_fadesp.model.Pagamento;
import com.challenge_fadesp.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
  List<Pagamento> findByCodigoDebito(Integer codigoDebito);

  List<Pagamento> findByIdentificadorPagamento(String identificadorPagamento);

  List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
}
