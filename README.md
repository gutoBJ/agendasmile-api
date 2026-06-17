# AgendaSmile API

API REST para gerenciamento de consultas odontológicas desenvolvida com Java Spring Boot.

## Sobre o Projeto

O AgendaSmile API é o backend responsável pelo gerenciamento de consultas odontológicas, autenticação de usuários, controle de pacientes, dentistas, especialidades e consultas, disponibilizando uma API REST segura para consumo pelo frontend Angular.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven

## Arquitetura

```text
Cliente (Angular)
       │ HTTP/REST
       ▼
AgendaSmile API (Spring Boot)
       │ JPA/Hibernate
       ▼
PostgreSQL
```

## Funcionalidades

- Autenticação via JWT
- Gerenciamento de Usuários
- Gerenciamento de Pacientes
- Gerenciamento de Dentistas
- Gerenciamento de Especialidades
- Agendamento de Consultas
- Cancelamento de Consultas
- Dashboard e Relatórios

## Regras de Negócio

- Não permite conflito de horários para o mesmo dentista.
- Não permite agendamentos em datas passadas.
- Cancelamentos exigem motivo.
- Somente administradores podem gerenciar usuários.
- Dentistas visualizam apenas suas próprias consultas.
- Administradores visualizam todas as consultas.
- A data final da consulta deve ser posterior à inicial.

## Estrutura do Projeto

```text
src/main/java
├── controller
├── service
├── enums
├── exception
├── repository
├── entity
├── dto
├── security
└── utils
```

## Configuração do Banco de Dados

Crie um banco PostgreSQL:

```sql
CREATE DATABASE sistema_gestao_consultas;
```

### application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_gestao_consultas
spring.datasource.username=postgres
spring.datasource.password=#bj_Post123
```

## Como Executar

### Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL 15+

### Clonar o repositório

```bash
git clone https://github.com/gutoBJ/agendasmile-api.git
```

### Entrar na pasta do projeto

```bash
cd backend
```

### Executar a aplicação

```bash
mvn spring-boot:run
```

API disponível em:

```text
http://localhost:8080
```

## Autenticação

### Login

**Método:** `POST`

```http
/api/auth/login
```

### Body

```json
{
  "email": "admin@agendaSmile.com",
  "senha": "admin123"
}
```

### Resposta

```json
{
  "token": "jwt-token"
}
```

## Endpoints

### Usuários

| Método | Endpoint |
|---------|---------|
| GET | /usuarios |
| GET | /usuarios/{id} |
| POST | /usuarios |
| PUT | /usuarios/{id} |
| DELETE | /usuarios/{id} |

### Pacientes

| Método | Endpoint |
|---------|---------|
| GET | /pacientes |
| GET | /pacientes/{id} |
| POST | /pacientes |
| PUT | /pacientes/{id} |

### Dentistas

| Método | Endpoint |
|---------|---------|
| GET | /dentistas |
| GET | /dentistas/{id} |
| POST | /dentistas |
| PUT | /dentistas/{id} |

### Especialidades

| Método | Endpoint |
|---------|---------|
| GET | /especialidades |
| POST | /especialidades |

### Consultas

| Método | Endpoint |
|---------|---------|
| GET | /consultas |
| POST | /consultas |
| PUT | /consultas/{id} |
| PATCH | /consultas/{id}/cancelar |

## Autor

**Augusto Chupernate**
