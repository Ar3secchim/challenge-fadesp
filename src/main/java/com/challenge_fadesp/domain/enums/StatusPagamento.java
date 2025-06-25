package com.challenge_fadesp.domain.enums;

import java.util.Set;


/**
 * Enum que representa os vários status de um pagamento
 * Cada status pode transitar para status específicos e pode executar ações ao transitar.
 */
public enum StatusPagamento {
  PENDENTE_PROCESSAMENTO {
    @Override
    public Set<StatusPagamento> getTransicoesPermitidas() {
      return Set.of(PROCESSADO_SUCESSO, PROCESSADO_FALHA);
    }
  },

  PROCESSADO_SUCESSO {
    @Override
    public Set<StatusPagamento> getTransicoesPermitidas() {
      return Set.of();
    }
  },

  PROCESSADO_FALHA {
    @Override
    public Set<StatusPagamento> getTransicoesPermitidas() {
      return Set.of(PENDENTE_PROCESSAMENTO);
    }
  };

  public abstract Set<StatusPagamento> getTransicoesPermitidas();

  public boolean podeTransicionarPara(StatusPagamento novoStatus) {
    return getTransicoesPermitidas().contains(novoStatus);
  }
}