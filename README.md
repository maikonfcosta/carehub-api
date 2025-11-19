# 🏥 CareHub API - Serviço de Agendamento Médico

Serviço de Back-end responsável pela lógica de negócio, persistência de dados (PostgreSQL) e exposição dos endpoints REST para o Front-end.

## ⚙️ Tecnologias Principais

| Componente | Tecnologia | Versão Principal |
| :--- | :--- | :--- |
| **Framework** | **Spring Boot** | 3.5.7 |
| **Linguagem** | **Java** | 21 (LTS) |
| **Banco de Dados** | **PostgreSQL** | 12+ |
| **Dependências ORM** | Spring Data JPA / Hibernate | 6.x |
| **Build Tool** | **Maven** | 3.x |
| **Deploy** | **Render.com** (via Docker) | - |

## 🚀 Como Rodar Localmente (Desenvolvimento)

Certifique-se de ter o **JDK 21** e o **PostgreSQL** instalados e em execução na porta padrão (`5432`).

### 1. Configuração do Banco de Dados

Crie um banco de dados vazio no seu servidor PostgreSQL local (ex: `carehub_db`).

### 2. Configuração do Arquivo de Recursos

Ajuste o arquivo `src/main/resources/application.yml` com suas credenciais de desenvolvimento local:

# Bloco Padrão (Desenvolvimento Local)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/carehub_db
    username: [SEU_USUARIO_LOCAL]
    password: [SUA_SENHA_LOCAL]
  jpa:
    hibernate:
      ddl-auto: update # Cria/atualiza as tabelas automaticamente em desenvolvimento
```

### 3. ExecuçãoUse sua IDE (IntelliJ, VS Code) 
para rodar a classe principal (CarehubApiApplication) ou use o Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em ```http://localhost:8080.```

## 🌐 Endpoints Principais

| Recurso | Método | Descrição |
| :--- | :--- | :--- |
| ```/api/pacientes``` | ```POST``` | Cadastra um novo paciente. |
| ```/api/medicos``` | ```POST``` | Cadastra um novo médico (validação de CRM único). |
| ```/api/agendamentos``` | ```POST``` | Agenda uma nova consulta (validação de conflito de horário). |
| ```/api/cep/{cep}``` | ```GET``` | **NOVO:** Consulta endereço completo via ViaCEP. |

## ☁️ Deploy e Variáveis de Produção (Render)

O deploy é feito via **Docker** no **Render.com**, ativando o perfil prod.

**Variáveis de Ambiente (Web Service/Container:**

| Variável | Valor | Uso |
| :--- | :--- | :--- |
| ```DATABASE_URL``` | URL JDBC completa do Render DB | Conexão do Spring Boot. |
| ```DB_USERNAME``` | Usuário do DB de Produção | Usado pelo pool de conexões. |
| ```DB_PASSWORD``` | Senha do DB de Produção | Usado pelo pool de conexões. |
