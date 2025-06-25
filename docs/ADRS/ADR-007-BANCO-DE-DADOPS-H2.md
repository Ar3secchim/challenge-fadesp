# ADR-007 – Banco de Dados H2

**Data:** 25 de junho de 2023
**Status:** Aceito

---

## Contexto

Durante o desenvolvimento da API de Pagamentos, inicialmente adotamos o PostgreSQL como banco de dados principal (conforme ADR-002), executado via Docker. Entretanto, identificamos algumas necessidades específicas para o ambiente de desenvolvimento e testes:

1. Configuração mais simples para novos desenvolvedores
2. Inicialização mais rápida em ambientes de desenvolvimento
3. Execução facilitada de testes automatizados
4. Redução de dependências externas para rodar o projeto localmente

Estas necessidades motivaram a avaliação de alternativas ao PostgreSQL para determinados ambientes.

---

## Decisão

Decidimos adotar o banco de dados H2 como alternativa ao PostgreSQL para ambientes de desenvolvimento e testes. O H2 é um banco de dados em memória que pode ser facilmente incorporado à aplicação Spring Boot.

A implementação foi realizada com as seguintes características:

1. Configuração no `application.properties` (ou `application-dev.properties`):

```properties
spring.datasource.url=jdbc:h2:mem:pagamentosdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

2. Adição da dependência no `pom.xml`:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

3. Manutenção da compatibilidade dos scripts Flyway entre H2 e PostgreSQL

---

## Consequências

| Tipo        | Impacto                                                                              |
|-------------|--------------------------------------------------------------------------------------|
| **Positivo**| - Inicialização mais rápida da aplicação em ambiente de desenvolvimento.<br>- Não requer instalação ou configuração de Docker para executar o banco.<br>- Simplificação da execução de testes automatizados.<br>- Console web integrado para facilitar visualização dos dados.<br>- Banco de dados limpo a cada reinicialização (ideal para testes). |
| **Negativo**| - Possível discrepância entre ambientes de desenvolvimento (H2) e produção (PostgreSQL).<br>- Limitações do H2 em suportar recursos específicos do PostgreSQL.<br>- Scripts SQL podem exigir adaptações para compatibilidade entre os dois bancos.<br>- Dados não são persistidos após reinicialização da aplicação. |

---