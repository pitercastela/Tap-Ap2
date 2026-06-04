# API de Gestão Acadêmica - Spring Boot

**Instituição:** IBMEC  
**Disciplina:** Técnicas Avançadas de Programação (IBM3120)
**Semestre:** 1º Semestre de 2026  
**Professor:** Thiago Souza
**Aluno:** Lucas Alves Castela Pereira  

---

## 📌 Sobre o Projeto
Este projeto é uma API RESTful desenvolvida em **Java com Spring Boot**, construída para gerenciar o escopo acadêmico de alunos, cursos, disciplinas e matrículas. 

O diferencial arquitetural desta aplicação é a implementação **manual do acesso a dados via JDBC**, substituindo as abstrações do Spring Data JPA para demonstrar a aplicação prática e purista de conceitos de Engenharia de Software e **Design Patterns** (Singleton e Factory Method), conforme exigência acadêmica.

## 🚀 Tecnologias e Infraestrutura
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3+
* **Redução de Boilerplate:** Lombok
* **Documentação:** SpringDoc OpenAPI (Swagger UI)
* **Banco de Dados:** MySQL (Hospedado em nuvem via **Clever Cloud**)
* **Deploy/Hospedagem:** **Render**

## 📐 Arquitetura e Design Patterns Aplicados

Para garantir o baixo acoplamento e a alta coesão, o sistema foi desenhado respeitando os princípios do S.O.L.I.D. e implementando os seguintes padrões de projeto:

### 1. Singleton (Gerenciamento de Conexão)
A classe `ConexaoSingleton` garante que toda a aplicação compartilhe uma única instância de `java.sql.Connection` com o banco de dados da Clever Cloud, evitando sobrecarga de conexões concorrentes e garantindo o controle centralizado do driver JDBC.

### 2. Factory Method com Reflection (Injeção Dinâmica de DAOs)
Implementado através da classe `DaoFactory`. Em vez de os *Controllers* instanciarem os *Data Access Objects* (DAOs) diretamente, eles delegam essa responsabilidade à fábrica. 
* A fábrica utiliza **Reflection** (`.getDeclaredConstructor().newInstance()`) para instanciar dinamicamente qualquer DAO solicitado.
* Garante aderência ao **Open/Closed Principle (OCP)**: novas tabelas e DAOs podem ser adicionados ao sistema sem necessidade de alterar o código da fábrica.

## 🗄️ Estrutura do Banco de Dados
O sistema utiliza um banco SQL relacional com as seguintes tabelas:
* `cursos` (1:N com alunos)
* `disciplinas` (N:M com alunos)
* `alunos` (N:1 com cursos)
* `aluno_disciplina` (Tabela associativa / Matrículas)

## 🌐 Endpoints e Documentação Interativa (Swagger)

A API utiliza a passagem de dados via parâmetros de URL (`@RequestParam` e `@PathVariable`), padronizando o consumo das rotas.

Você pode testar a aplicação em tempo real diretamente pela interface interativa do Swagger gerada automaticamente através do link incluído dentro do repositório.
