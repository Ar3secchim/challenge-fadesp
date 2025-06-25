package com.challenge_fadesp.services.pagamento.impl;

import com.challenge_fadesp.dtos.FiltrarPagamentoRequestDTO;
import com.challenge_fadesp.dtos.PagamentoResponseDTO;
import com.challenge_fadesp.exception.pagamentos.OperacaoInvalidaException;
import com.challenge_fadesp.exception.pagamentos.StatusInvalidoException;
import com.challenge_fadesp.mapper.PagamentoMapper;
import com.challenge_fadesp.model.entity.Pagamento;
import com.challenge_fadesp.model.enums.StatusPagamento;
import com.challenge_fadesp.repository.PagamentoRepository;
import com.challenge_fadesp.services.pagamento.usecase.ListarPagamentosUseCase;
import com.challenge_fadesp.utils.PagamentoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarPagamentoUseCaseImpl implements ListarPagamentosUseCase {
  private final PagamentoRepository pagamentoRepository;
  private final PagamentoMapper pagamentoMapper;

  public ListarPagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
    this.pagamentoRepository = pagamentoRepository;
    this.pagamentoMapper = pagamentoMapper;
  }


  @Override
  public List<PagamentoResponseDTO> listarTodos() {
    return pagamentoRepository.findAll().stream()
      .map(pagamentoMapper::toResponseDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<PagamentoResponseDTO> filtrar(FiltrarPagamentoRequestDTO requestDTO) {
    Integer codigoDebito = requestDTO.getCodigoDebito();
    String identificadorPagamento = requestDTO.getIdentificadorPagamento();
    StatusPagamento status = requestDTO.getStatusPagamento();

    if (codigoDebito == null && identificadorPagamento == null && status == null) {
      return listarTodos();
    }

    Specification<Pagamento> spec = Specification.where(PagamentoSpecification.comCodigoDebito(codigoDebito))
      .and(PagamentoSpecification.comIdentificador(identificadorPagamento))
      .and(PagamentoSpecification.comStatus(status));

    List<Pagamento> pagamentos = pagamentoRepository.findAll(spec);

    return pagamentos.stream()
      .map(pagamentoMapper::toResponseDTO)
      .collect(Collectors.toList());
  }
}
