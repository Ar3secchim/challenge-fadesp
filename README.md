# 💳 Desafio Técnico - API de Pagamentos

Este projeto é uma API REST desenvolvida em **Java 17 com Spring Boot** para processar pagamentos de pessoas físicas e jurídicas. A API permite registrar pagamentos, atualizar status, consultar e aplicar filtros, além de realizar exclusão lógica conforme regras de negócio.

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* PostgreSQL (via Docker)
* Flyway (migrations)
* Lombok (boilerplate reduction)
* JUnit & Mockito (testes)
* Swagger/OpenAPI 

---

## 📐 Diagrama UML

A seguir, o diagrama de entidade principal `Pagamento`, com os enums relacionados:

![diagrama-classes](docs/image.png)

* `Pagamento`: representa um pagamento com método, valor, status e dados do pagador.
* `MetodoPagamento`: enum com BOLETO, PIX, CARTAO_CREDITO e CARTAO_DEBITO.
* `StatusPagamento`: enum com PENDENTE_PROCESSAMENTO, PROCESSADO_SUCESSO e PROCESSADO_FALHA.

---

## 🧱 Estrutura do Projeto

```
src/
├── controller/     # Endpoints REST
├── dto/            # Objetos de transferência de dados
├── service/        # Regras de negócio
├── repository/     # Acesso a dados com Spring Data JPA
├── model/          # Entidades JPA
├── mapper/         # Conversão entre entidades e DTOs
└── exception/       # Tratamento de erros centralizado
```

---

## ▶️ Rodando o Projeto Localmente
### Pré-requisitos

* Java 17 instalado
* Docker e Docker Compose instalados

### Rodando o projeto

1. Clone o repositório:
```bash
   git clone https://github.com/Ar3secchim/challenge-fadesp.git
   cd challenge-fadesp
```

Inicie a aplicação Spring Boot:
    Inicia h2 e faz as migrates com o Flyway e sobe o banco de dados
```bash
  ./mvnw spring-boot:run
```

Acesse a documentação Swagger em: http://localhost:8080/swagger-ui.html

## 🔎 Endpoints Principais

| Método | Rota                                | Descrição                          |
|--------|-------------------------------------|------------------------------------|
| POST   | `api/pagamentos`                    | Criar novo pagamento               |
| GET    | `api/pagamentos`                    | Listar todos  pagamentos           |
| GET    | `api/pagamentos/{id} `              | Buscar um pagamento por id         |
| PATCH  | `api/pagamentos/{id}/status`        | Atualizar status do pagamento      |
| POST   | `api/pagamentos/filtrar-pagamentos` | Listar pagamentos com filtros      |
| PATCH  | `apis/pagamentos/{id}`              | Desativar pagamento (regra lógica) |

### 📥 Exemplo de payload para criação de pagamento (POST `/pagamentos`)

```json
{
  "codigoDebito": 12345,
  "identificadorPagamento": "12345678909",
  "metodoPagamento": "CARTAO_CREDITO",
  "numeroCartao": "4111111111111111",
  "valor": 150.75
}
```

---

## 🧠 Regras de Negócio (Status)

* `PENDENTE_PROCESSAMENTO` → pode virar `PROCESSADO_SUCESSO` ou `PROCESSADO_FALHA`
* `PROCESSADO_FALHA` → pode voltar para `PENDENTE_PROCESSAMENTO`
* `PROCESSADO_SUCESSO` → não pode ser alterado

### 🔐 Autenticação

> Esta API não requer autenticação para os endpoints fornecidos (desafio técnico).

---

## 📂 ADRs e Documentação Técnica

Todas as decisões arquiteturais estão documentadas na pasta `docs/ADRS`:

* `ADR-001`: Spring Boot com Java 17
* `ADR-002`: PostgreSQL via Docker (ultrapassada a decisão de usar H2 -> ADR-007)
* `ADR-003`: Estrutura em Camadas
* `ADR-004`: Spring Data JPA
* `ADR-005`: Lombok
* `ADR-006`: Flyway
* `ADR-007`: Banco de Dados H2
---

## 🧪 Testes
Os testes estão localizados na pasta `src/test/java` e cobrem:
* Testes unitários para serviços e controllers
* Testes de integração para endpoints REST
* Testes de validação de regras de negócio
* Testes de exceções e tratamento de erros

### 📊 Cobertura Atual: **71%** (460/645 instruções)

### 📋 Executar Testes
```bash
# Executar todos os testes
./mvnw clean test

# Gerar relatório de cobertura
./mvnw jacoco:report

# Visualizar relatório
xdg-open target/site/jacoco/index.html
```

### 📚 Documentação Completa de Testes
* [📊 **Relatório de Cobertura**](docs/COBERTURA-TESTES.md) - Métricas detalhadas e análise



