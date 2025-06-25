# ADR-002 – Banco de Dados PostgreSQL via Docker

**Data:** 21 de junho de 2025  
**Status:** Deprecioada

---

## Contexto

A API REST para processamento de pagamentos precisa armazenar informações de forma estruturada, segura e com suporte a transações. A escolha do sistema de gerenciamento de banco de dados (SGBD) deve considerar confiabilidade, suporte à integridade referencial, ampla adoção e facilidade de configuração em ambiente de desenvolvimento e testes. Além disso, é desejável que o ambiente de banco possa ser facilmente reproduzido em diferentes máquinas.

---

## Decisão

Foi decidido o uso do banco de dados relacional PostgreSQL, executado via container Docker. O PostgreSQL é amplamente adotado, gratuito e oferece recursos robustos como transações ACID, suporte a JSON, integridade referencial, funções e extensões. O uso de Docker para executar o banco facilita a configuração local e a padronização entre os ambientes de desenvolvimento, testes e deploy, garantindo consistência e minimizando problemas relacionados a diferenças de configuração.

---

## Consequências

| Tipo        | Impacto                                                                                                   |
|-------------|-----------------------------------------------------------------------------------------------------------|
| **Positivo**| - Facilidade de instalação e configuração via Docker.<br>- Banco de dados robusto e com ampla comunidade.<br>- Suporte completo a recursos relacionais e não relacionais (JSON). |
| **Negativo**| - Requer instalação do Docker nos ambientes locais.<br>- Overhead leve de containerização durante o desenvolvimento. |

---