# PROPTI - Otimizador de Produção

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-Build-646CFF?style=for-the-badge&logo=vite&logoColor=white)

<img width="1878" height="964" alt="image" src="https://github.com/user-attachments/assets/b883952d-bd64-421c-8591-c46a4cfe71f0" />


## 📋 Sobre o Projeto

O **PROPTI** (Product Optimization) é um sistema Full Stack desenvolvido para gerenciamento de insumos e otimização de produção. O objetivo principal da aplicação é auxiliar gestores na tomada de decisão sobre o que produzir, maximizando o lucro com base no estoque de matérias-primas disponível.

O sistema gerencia o cadastro de matérias-primas e produtos (com suas respectivas receitas/composições). O diferencial é seu algoritmo de otimização que analisa o estoque atual e sugere a combinação ideal de produtos a serem fabricados para obter o maior retorno financeiro possível. A lógica implementada resolve um problema de alocação de recursos similar ao **Knapsack Problem** (Problema da Mochila) utilizando uma estratégia gulosa (Greedy Algorithm) focada no valor de venda.

## 🚀 Tecnologias Utilizadas

### Backend (API)
*   **Java 21**: Linguagem base, utilizando recursos modernos.
*   **Spring Boot 3**: Framework para criação de microsserviços robustos.
*   **Spring Data JPA**: Persistência de dados e abstração de repositórios.
*   **Flyway**: Versionamento e migração de banco de dados.
*   **Validation**: Validação de dados de entrada (DTOs).
*   **Lombok**: Redução de código boilerplate.
*   **MapStruct**: Mapeamento eficiente entre Entidades e DTOs.
*   **SpringDoc OpenAPI**: Documentação automática da API (Swagger).

### Frontend (App)
*   **Vue.js 3**: Framework progressivo com Composition API.
*   **Vite**: Tooling de frontend para build rápido.
*   **Bootstrap 5**: Estilização e componentes responsivos.
*   **Axios**: Cliente HTTP para comunicação com o backend.
*   **Lucide Vue**: Biblioteca de ícones.

### Banco de Dados
*   **PostgreSQL**: Banco de dados relacional.

## 🏗️ Arquitetura e Padrões

O projeto segue os princípios de **Clean Code** e uma arquitetura em camadas bem definida para garantir manutenibilidade e escalabilidade:

*   **Controller**: Camada responsável por expor os endpoints REST.
*   **Service**: Camada que encapsula as regras de negócio, incluindo o algoritmo de otimização.
*   **Repository**: Camada de acesso a dados (Data Access Layer).
*   **DTO (Data Transfer Object)**: Padrão utilizado para trafegar dados entre o frontend e o backend, garantindo desacoplamento da camada de persistência.
*   **Mapper**: Separação da lógica de conversão de objetos.

## ⚙️ Pré-requisitos

Para rodar o projeto localmente, você precisará ter instalado:

*   **Java JDK 21** ou superior.
*   **Node.js** (versão 20 ou superior recomendada).
*   **PostgreSQL** (local ou via Docker).
*   **Docker & Docker Compose** (opcional, para rodar todo o ambiente).

## 🏃‍♂️ Como Rodar o Projeto

### 🐳 Opção Rápida: Docker Compose (Backend + Banco)

Se você tiver o Docker instalado, pode subir o banco de dados e a API com um único comando:

```bash
# Na raiz do projeto
docker-compose up --build
```

---

### Opção Manual:

### 1. Configuração do Banco de Dados

Crie um banco de dados PostgreSQL com o nome definido no `application.properties` (padrão: `factory-challenge-db`).

```sql
CREATE DATABASE "factory-challenge-db";
Nota: As credenciais padrão configuradas são usuário postgres e senha postgres. Ajuste o arquivo api/src/main/resources/application.properties se necessário.
```

### 2. Executando o Backend

Navegue até a pasta api e execute o projeto com o Maven Wrapper:

``` bash
cd api
# Instalar dependências e compilar
./mvnw clean install

# Rodar a aplicação
./mvnw spring-boot:run
O servidor iniciará na porta 8080.
```

### 3. Executando o Frontend

Navegue até a pasta app, instale as dependências e inicie o servidor de desenvolvimento:

```
bash
cd app
# Instalar dependências
npm install

# Rodar em modo de desenvolvimento
npm run dev
O frontend estará disponível geralmente em http://localhost:5173.
```

📖 Documentação da API
Após iniciar o backend, a documentação completa dos endpoints (Swagger UI) pode ser acessada em:

👉 http://localhost:8080/swagger-ui.html

### Funcionalidades:

- Gestão de Matérias-Primas:
	- Cadastro, edição, listagem e remoção de insumos.
	- Controle de quantidade disponível em estoque.

- Gestão de Produtos:
	- Cadastro de produtos com preço de venda.
	- Definição de Receita/Composição: Vínculo de múltiplas matérias-primas e quantidades necessárias para produzir uma unidade.

- Otimização de Produção:
	- Cálculo automático da quantidade máxima de produtos que podem ser fabricados com o estoque atual.
	- Priorização baseada no valor de venda para maximizar o lucro estimado.
	- Baixa automática (simulada ou real) de estoque conforme a sugestão aceita.
	- Desenvolvido como parte de um Teste Prático de P&D.
