# ADR-006 – Uso do Flyway para Versionamento de Banco de Dados

**Data:** 21 de junho de 2025  
**Status:** Aceito

---

## Contexto

Ao longo do desenvolvimento de uma aplicação, o banco de dados evolui com novas tabelas, colunas, constraints e dados de inicialização. Sem um controle de versionamento, essas alterações podem se perder ou causar inconsistências entre ambientes (desenvolvimento, testes, produção). Era necessário adotar uma ferramenta confiável para gerenciar essas mudanças de forma rastreável e automatizada.

---

## Decisão

Foi decidido utilizar o **Flyway** como ferramenta de versionamento de banco de dados. O Flyway permite o controle e execução automatizada de scripts SQL, numerados sequencialmente, garantindo que todas as alterações sejam aplicadas na ordem correta e de forma idempotente.

Ele se integra de forma nativa com o Spring Boot, sendo executado automaticamente ao subir a aplicação. Isso facilita a manutenção do histórico de alterações, permite reverter mudanças em caso de erro e assegura que todos os ambientes compartilhem a mesma estrutura de banco.

---

## Consequências

| Tipo        | Impacto                                                                                                                    |
|-------------|----------------------------------------------------------------------------------------------------------------------------|
| **Positivo**| - Permite versionar e auditar todas as alterações no schema do banco.<br>- Evita divergências entre ambientes.<br>- Integração automática com Spring Boot. |
| **Negativo**| - Exige disciplina no nome e na ordem dos scripts.<br>- Introduz mais uma dependência externa no projeto. |

---