# ADR-003 – Estrutura em Camadas da Arquitetura

**Data:** 21 de junho de 2025  
**Status:** Aceito

---

## Contexto

O projeto tem como premissa a simplicidade, clareza e boa organização do código. Para isso, é necessário definir uma estrutura de camadas que favoreça a separação de responsabilidades, facilite os testes, permita a reutilização de código e torne o sistema mais manutenível. Uma arquitetura desorganizada poderia gerar acoplamento excessivo, dificultando a evolução e correção de erros ao longo do tempo.

---

## Decisão

Foi adotada uma arquitetura baseada em camadas organizadas conforme o padrão MVC (Model-View-Controller), adaptado para backend REST com Spring Boot. A estrutura proposta inclui:

- **Controller:** expõe os endpoints da API e lida com as requisições HTTP.
- **DTO (Data Transfer Object):** encapsula os dados trafegados entre cliente e servidor, evitando acoplamento direto com as entidades.
- **Service:** contém a lógica de negócio da aplicação.
- **Repository:** lida com a persistência e consulta ao banco de dados, via Spring Data JPA.
- **Model (Entity):** representa as entidades do domínio persistidas no banco.
- **Mapper:** responsável por converter entre DTOs e entidades.
- **Exception:** camada dedicada para captura e tratamento centralizado de exceções.

Essa organização permite que cada parte do sistema tenha uma responsabilidade bem definida, promovendo testabilidade e clareza no fluxo da aplicação.

---

## Consequências

| Tipo        | Impacto                                                                                                                   |
|-------------|---------------------------------------------------------------------------------------------------------------------------|
| **Positivo**| - Separação clara de responsabilidades.<br>- Código mais limpo e testável.<br>- Facilidade de manutenção e expansão futura. |
| **Negativo**| - Maior número de arquivos e classes pode parecer excessivo para projetos muito pequenos.<br>- Leve curva de aprendizado para novos desenvolvedores. |

---