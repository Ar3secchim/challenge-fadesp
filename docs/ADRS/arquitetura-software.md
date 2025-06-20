# Arquitetura de Software

## Contexto Geral

O projeto visa atender a um desafio técnico de backend, focado na criação de uma API REST para gerenciamento de pagamentos com diferentes métodos (boleto, pix, cartão de crédito e débito). A API deve ser simples, clara, segura e eficiente. Serão utilizados Java 17 com Spring Boot, banco de dados relacional (PostgreSQL ou MySQL), e o deploy será feito via Docker, garantindo reprodutibilidade.

## Objetivos Principais

* Garantir um sistema simples, bem estruturado e de fácil entendimento.
* Facilitar manutenção e expansão futura.
* Adotar boas práticas de desenvolvimento.
* Manter o código limpo, testável e documentado.

## Decisões de Arquitetura

### Framework Escolhido: Spring Boot com Java 17

`ADR 001`

**Justificativa:**
Spring Boot fornece uma base robusta para desenvolvimento de APIs RESTful, com integração facilitada a bancos de dados, validação de dados, segurança e testes. A escolha da versão 17 do Java garante compatibilidade com recursos modernos e maior longevidade do código.

### Banco de Dados: PostgreSQL via Docker

`ADR 002`

**Justificativa:**
PostgreSQL é um banco relacional robusto, com suporte completo a transações, integridade referencial e extensibilidade. Utilizá-lo via Docker simplifica o setup local e garante ambiente de testes reproduzível.

### Camadas do Projeto

`ADR 003`

* **Controller:** entrada de requisições HTTP.
* **DTOs:** representação dos dados trafegados pela API.
* **Service:** regras de negócio e orquestração.
* **Repository:** abstração de acesso ao banco de dados via Spring Data JPA.
* **Model:** entidades JPA.
* **Mapper:** conversão entre DTO e entidade.
* **Exception:** tratamento centralizado de erros com `@ControllerAdvice`.

### Persistência com Spring Data JPA

`ADR 004`

**Justificativa:**
Spring Data JPA reduz o boilerplate para operações de banco de dados ao permitir a criação de repositórios baseados em interfaces, eliminando a necessidade de escrever implementações repetitivas de CRUD. Ele se integra de forma nativa ao Spring Boot, oferecendo suporte automático a transações, paginação, ordenação e queries derivadas de nomes de métodos. Isso permite que o foco do desenvolvimento permaneça na lógica de negócio, sem se preocupar com detalhes de persistência.

### Uso de Lombok

`ADR 005`

**Justificativa:**
Lombok é uma biblioteca Java que ajuda a reduzir a verbosidade do código, especialmente em classes modelo, ao gerar automaticamente getters, setters, construtores, métodos `equals`, `hashCode`, `toString` e muito mais por meio de anotações. Isso melhora a legibilidade do código e reduz o risco de erros manuais.

### Controle de Migrations com Flyway

`ADR 006`

**Justificativa:**
Flyway permite o versionamento e gerenciamento de scripts SQL de forma automatizada e confiável. Com ele, conseguimos manter o histórico das alterações no banco de dados, garantir consistência entre ambientes e facilitar o setup de ambientes de desenvolvimento e produção, assegurando que todos tenham a mesma estrutura de dados.

## Estrutura de Projeto

```
src/
└── docs/
    ├── ADRS/ #Architecture Decision Record (Registro de Decisão de Arquitetura)
    └── arquitetura-software.md
└── main/
    ├── java/
    │   └── com.challenge_fedesp/
    │       ├── controller/
    │       ├── dto/
    │       ├── service/
    │       ├── repository/
    │       ├── model/
    │       ├── mapper/
    │       └── exception/
    └── resources/
        ├── application.yml
        └── db/init.sql
```
