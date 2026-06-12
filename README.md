## 📌 Sobre o Projeto
Este projeto é uma API RESTful desenvolvida em **Java com Spring Boot**, projetada para atender ao minimundo de "Gestão da Copa do Mundo" (FIFA World Cup). O sistema permite o gerenciamento completo de seleções, jogadores, partidas e o registro de participações.

O grande diferencial arquitetural desta aplicação é a implementação **manual e purista do acesso a dados via JDBC** (sem a utilização de frameworks de abstração como o Spring Data JPA). O objetivo central é demonstrar a aplicação prática de Engenharia de Software e **Design Patterns**, garantindo baixo acoplamento e alta coesão, conforme as exigências da disciplina.

## 🚀 Tecnologias e Infraestrutura
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3+
* **Redução de Boilerplate:** Lombok
* **Documentação:** SpringDoc OpenAPI (Swagger UI)
* **Banco de Dados:** MySQL (Hospedado em nuvem via **Aiven**)
* **Deploy/Hospedagem da API:** **Render**

## 📐 Arquitetura e Design Patterns Aplicados

O projeto foi construído respeitando os princípios do **S.O.L.I.D.**, materializados através das seguintes técnicas:

### 1. Padrão Singleton (Gerenciamento de Conexão)
A classe `ConexaoSingleton` garante uma única instância global de `java.sql.Connection`. Isso evita o esgotamento do *pool* de conexões do banco de dados na nuvem e centraliza o controle do *driver* JDBC e das credenciais (incluindo o rigoroso protocolo SSL exigido pelo Aiven).

### 2. Factory Method com Java Reflection (Injeção Dinâmica)
Implementado através da `DaoFactory`. A responsabilidade de instanciar os Data Access Objects (DAOs) foi removida dos *Controllers* e isolada em uma fábrica genérica que utiliza Reflection (`.getDeclaredConstructor().newInstance()`). Isso garante aderência ao **Open/Closed Principle (OCP)**: o sistema está fechado para modificações estruturais, mas aberto para a expansão de novas tabelas.

### 3. Padrão DAO e DTO
Isolamento absoluto entre as regras de negócio HTTP (Controllers) e a linguagem SQL. Entidades de domínio possuem seus DAOs dedicados, enquanto tabelas associativas (N:M) retornam objetos mais leves de transferência de dados (DTOs), como o `ParticipacaoDTO`, evitando a construção de grafos de objetos desnecessariamente pesados.

## 🗄️ Modelagem do Banco de Dados (Minimundo)
A arquitetura relacional foi desenhada sem o uso de `AUTO_INCREMENT`, transferindo para a aplicação/usuário o controle absoluto das chaves de identificação (ideal para o mapeamento de códigos oficiais da FIFA).

* **`selecoes`** (1:N com jogadores)
* **`jogadores`** (N:1 com seleções)
* **`partidas`** (N:M com seleções)
* **`selecao_partida`** (Classe associativa / Tabela de Junção)
