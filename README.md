# 🏥 CareHub API - Serviço de Agendamento Médico

Serviço de Back-end responsável pela lógica de negócio, persistência de dados (PostgreSQL) e exposição dos endpoints REST para o Front-end.

## ⚙️ Arquitetura e Tecnologias

| Componente | Tecnologia | Versão Principal |
| :--- | :--- | :--- |
| **Framework** | **Spring Boot** | 3.5.7 |
| **Linguagem** | **Java** | 21 (LTS) |
| **Segurança** | **Spring Security** | 6.x |
| **Autenticação** | **Firebase Admin SDK (JWT)** | 9.3.0 |
| **Banco de Dados** | **PostgreSQL** | 12+ |
| **Pagamentos** | **Stripe Java SDK** | 25.x |
| **Deploy** | **Render.com** (via Docker) | - |

## 🚀 Como Rodar Localmente (Desenvolvimento)

### 1. Configuração de Variáveis Locais

Para iniciar localmente, defina a chave secreta do Stripe e as credenciais do DB no seu ambiente de execução ou `application.yml` (no perfil `default`).

> **Atenção:** A chave `FIREBASE_CREDENTIALS` deve ser injetada no ambiente local (IDE) como um JSON completo.

### 2. Execução

Use sua IDE ou o Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em ```http://localhost:8080```.

## 🌐 Endpoints Principais

| Recurso | Método | Descrição | Status de Segurança |
| :--- | :--- | :--- | :--- |
| /api/pacientes | CRUD | Gerenciamento completo de pacientes (CRUD). | Protegido (Token) | 
| /api/medicos | CRUD | Gerenciamento completo de médicos (CRUD). | Protegido (Token) |
| /api/agendamentos | POST/GET | Agendamento e listagem de consultas (Bloqueio de Horário). | Protegido (Token)| 
| /api/pagamentos/processar | POST | Processa cobrança via token Stripe. | Protegido (Token) |
| /api/relatorios/pagamentos | GET | Histórico de transações salvas. | Protegido (Token) |
| /api/cep/{cep} | GET | Consulta endereço ViaCEP. | Público (permitAll) |


## 🔒 Segurança e Deploy
- **Fluxo de Autenticação:** Acesso liberado apenas se o cabeçalho ```Authorization: Bearer <token>``` for validado com sucesso pelo Firebase Admin SDK.

- **Variáveis Críticas (Render):** O deploy exige as variáveis secretas ```DATABASE_URL```, ```STRIPE_SECRET_KEY``` e ```FIREBASE_CREDENTIALS``` injetadas no ambiente do contêiner.
