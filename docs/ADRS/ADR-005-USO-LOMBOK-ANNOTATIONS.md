# ADR-005 – Uso do Lombok

**Data:** 21 de junho de 2025  
**Status:** Aceito

---

## Contexto

Durante o desenvolvimento de aplicações Java, especialmente em projetos com muitas entidades e DTOs, é comum a criação repetitiva de métodos como `getters`, `setters`, `toString`, `equals`, `hashCode`, e construtores. Essa repetição aumenta o volume de código, dificulta a leitura e gera alto custo de manutenção.

---

## Decisão

Foi decidido utilizar a biblioteca **Project Lombok** no projeto. O Lombok reduz a verbosidade do código fonte ao gerar automaticamente métodos comuns por meio de anotações como `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@Builder`, entre outras. Com isso, o código fica mais enxuto, legível e focado na lógica de negócio.

Além disso, o Lombok é amplamente utilizado no ecossistema Spring e se integra bem com IDEs modernas como IntelliJ IDEA e Eclipse, desde que os plugins estejam instalados.

---

## Consequências

| Tipo        | Impacto                                                                                                                        |
|-------------|--------------------------------------------------------------------------------------------------------------------------------|
| **Positivo**| - Redução significativa da verbosidade do código.<br>- Aumento da legibilidade e foco na lógica de negócio.<br>- Facilita a manutenção de classes modelo. |
| **Negativo**| - Pode causar confusão para novos desenvolvedores que não conhecem a biblioteca.<br>- Requer instalação de plugin específico em algumas IDEs para pleno suporte. |

---