# ADR-004 – Uso do Spring Data JPA

**Data:** 21 de junho de 2025  
**Status:** Aceito

---

## Contexto

O projeto exige a persistência de dados em um banco relacional e a manipulação frequente de registros. O uso direto do JDBC implicaria em um grande volume de código repetitivo para implementar operações de leitura e escrita, dificultando a manutenção e a legibilidade. Era necessário adotar uma abordagem mais simples, segura e produtiva para o acesso a dados.

---

## Decisão

Foi decidido utilizar o Spring Data JPA para abstrair o acesso ao banco de dados. Com ele, é possível criar repositórios a partir de interfaces, reduzindo drasticamente o código necessário para operações CRUD. Além disso, o framework permite gerar queries automáticas com base em nomes de métodos, realizar paginação e ordenação, e fazer uso de JPQL ou consultas nativas quando necessário.

O Spring Data JPA também se integra perfeitamente ao Spring Boot, proporcionando suporte a transações, injeção de dependências e tratamento de exceções de forma padronizada.

---

## Consequências

| Tipo        | Impacto                                                                                                                       |
|-------------|-------------------------------------------------------------------------------------------------------------------------------|
| **Positivo**| - Redução significativa de código boilerplate.<br>- Integração nativa com o Spring Boot.<br>- Facilidade para escrever queries dinâmicas e customizadas. |
| **Negativo**| - Necessidade de compreender o funcionamento do JPA e suas limitações.<br>- Possível dificuldade para debugar queries geradas automaticamente em alguns cenários. |

---