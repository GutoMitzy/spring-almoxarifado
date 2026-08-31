# Spring Almoxarifado

API REST para gerenciamento de um sistema de almoxarifado, desenvolvida com Spring Boot, utilizando persistência relacional, autenticação baseada em JWT e arquitetura em camadas. Testes unitários com JUnit e Mockito.

## Objetivo

Este é um projeto pessoal desenvolvido com o objetivo de praticar desenvolvimento de APIs REST, persistência de dados, autenticação, arquitetura de software, validação e testes, tendo como base o exercício 12 do livro *Projeto de Banco de Dados - V4*, de Carlos Alberto Heuser.

### Arquitetura

A aplicação segue uma arquitetura em camadas:

* **Controller:** recebe as requisições HTTP e disponibiliza os endpoints da API.
* **Service:** concentra as regras de negócio e a lógica da aplicação.
* **Repository:** realiza a comunicação com o banco através do Spring Data JPA.
* **Model:** representa as entidades persistidas no banco de dados.
* **DTO:** define os objetos utilizados na entrada e saída dos dados da API.
* **Config:** concentra configurações da aplicação e segurança.
* **Exception / Handler:** centraliza o tratamento das exceções.
* **Enums:** representa valores enumerados utilizados pela aplicação.

## Operações da API

A API utiliza o prefixo:

```text
/v2/almoxarifado
```
### Documentação da API

A aplicação utiliza **SpringDoc OpenAPI** para documentação da API.

Com a aplicação executando, a interface do Swagger pode ser acessada através de:

```text
http://localhost:8080/swagger-ui/index.html
```


## Dependências principais

| Tecnologia              | Finalidade                              |
|-------------------------|-----------------------------------------|
| Java 25                 | Linguagem utilizada no projeto          |
| Spring Boot 4.1.0       | Framework principal                     |
| Spring Web MVC          | Desenvolvimento da API REST             |
| Spring Data JPA         | Persistência e acesso ao banco          |
| Spring Security         | Segurança e autenticação                |
| JJWT 0.12.6             | Implementação dos tokens JWT            |
| MySQL Connector         | Comunicação com o MySQL                 |
| H2                      | Banco utilizado em ambiente de testes   |
| SpringDoc OpenAPI 3.0.2 | Documentação da API                     |
| Jakarta Validation      | Validação dos dados                     |
| Lombok                  | Redução de código boilerplate           |
| Maven                   | Gerenciamento do projeto e dependências |
| Docker                  | Containerização                         |
| Docker Compose          | Orquestração dos containers             |
| LangChain4J             | Integração com LLM's                    |


## Autenticação

A aplicação utiliza **Spring Security** com autenticação baseada em **JWT (JSON Web Token)**.

O fluxo básico é:

```text
Cliente
   │
   │ POST /v2/almoxarifado/auth/login
   ▼
AuthenticationController
   │
   ▼
AuthenticationService
   │
   ▼
Token JWT
   │
   ▼
Cliente
   │
   │ Authorization: Bearer <token>
   ▼
Spring Security
   │
   ▼
Endpoint protegido
```

## Variáveis de ambiente

O Docker Compose utiliza um arquivo `.env` na raiz do projeto para fornecer as configurações necessárias aos containers.

Exemplo:

```env
DATABASE_NAME=
DATABASE_DEV=
DATABASE_USERNAME
DATABASE_PASSWORD=

JWT_KEY=
JWT_EXPIRATION=

RABBITMQ_PASSWORD=
RABBITMQ_USERNAME=

PROMETHEUS_USERNAME=
PROMETHEUS_PASSWORD=
```

Para execução local pelo IntelliJ, as variáveis devem ser configuradas no ambiente de execução da aplicação.


## Executando com Docker

Primeiramente, crie o arquivo `.env` na raiz do projeto.

Depois, execute:


```bash
docker compose up -d --build
```

## Executando em desenvolvimento

O projeto possui um profile `dev` configurado em `application-dev.yaml`.


Com o profile `dev` ativo:

```text
SPRING_PROFILES_ACTIVE=dev
```

A configuração atual de `application-dev.yaml` utiliza `localhost:3307` para conexão com o banco de desenvolvimento.


