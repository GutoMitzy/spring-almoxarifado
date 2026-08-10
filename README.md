# Spring Almoxarifado

API REST para gerenciamento de um sistema de almoxarifado, desenvolvida com Spring Boot, utilizando persistência relacional, autenticação baseada em JWT e arquitetura em camadas.

## Objetivo

Este é um projeto pessoal desenvolvido com o objetivo de praticar desenvolvimento de APIs REST, persistência de dados e arquitetura de software, tendo como base o exercício 12 do livro *Projeto de Banco de Dados - V4*, de Carlos Alberto Heuser.

## Estrutura

O projeto utiliza uma arquitetura em camadas, separando responsabilidades entre controllers, services, repositories, models, DTOs, configurações e tratamento de exceções.

### Operações da API

A API utiliza o prefixo `/v1/almoxarifado` para seus recursos.

#### Autenticação

| Método | Endpoint                         | Operação                            |
| ------ | -------------------------------- | ----------------------------------- |
| `POST` | `/v1/almoxarifado/auth/login`    | Realiza login e retorna o token JWT |
| `POST` | `/v1/almoxarifado/auth/register` | Registra um novo usuário            |

Os endpoints de autenticação são implementados pelo `AuthenticationController`.

#### Empresas

| Método | Endpoint                                | Operação                  |
| ------ | --------------------------------------- | ------------------------- |
| `GET`  | `/v1/almoxarifado/empresas`             | Lista todas as empresas   |
| `GET`  | `/v1/almoxarifado/empresas/{empresaId}` | Busca uma empresa pelo ID |
| `POST` | `/v1/almoxarifado/empresas`             | Cadastra uma nova empresa |

#### Peças

| Método | Endpoint                 | Operação               |
| ------ | ------------------------ | ---------------------- |
| `GET`  | `/v1/almoxarifado/pecas` | Lista todas as peças   |
| `POST` | `/v1/almoxarifado/pecas` | Cadastra uma nova peça |

#### Corredores

| Método | Endpoint                                                           | Operação                                               |
| ------ | ------------------------------------------------------------------ | ------------------------------------------------------ |
| `GET`  | `/v1/almoxarifado/corredores/{id}`                                 | Busca um corredor pelo ID                              |
| `GET`  | `/v1/almoxarifado/corredores/{corredorId}/page/{page}/size/{size}` | Lista os receptáculos de um corredor de forma paginada |

#### Entregas

| Método  | Endpoint                                            | Operação                         |
| ------- | --------------------------------------------------- | -------------------------------- |
| `POST`  | `/v1/almoxarifado/entregas`                         | Registra uma nova entrega        |
| `GET`   | `/v1/almoxarifado/entregas`                         | Lista todas as entregas          |
| `GET`   | `/v1/almoxarifado/entregas/page/{page}/size/{size}` | Lista entregas de forma paginada |
| `PATCH` | `/v1/almoxarifado/entregas/{entregaId}/concluir`    | Conclui uma entrega              |

---

### Dependências principais

| Dependência          | Finalidade                              |
| -------------------- | --------------------------------------- |
| Spring Data JPA      | Persistência e acesso ao banco de dados |
| Spring Web MVC       | Desenvolvimento da API REST             |
| Spring Security      | Autenticação e autorização              |
| MySQL Connector      | Comunicação com o banco MySQL           |
| Lombok               | Redução de código boilerplate           |
| SpringDoc OpenAPI    | Documentação da API e Swagger UI        |
| JJWT                 | Criação e validação de tokens JWT       |
| Spring Boot DevTools | Facilitação do desenvolvimento          |

### Arquitetura

A aplicação segue uma arquitetura em camadas:

* **Controller:** recebe as requisições HTTP e define os endpoints da API.
* **Service:** concentra as regras de negócio e a lógica da aplicação.
* **Repository:** realiza a comunicação com o banco de dados através do Spring Data JPA.
* **Model:** representa as entidades persistidas no banco.
* **DTO:** define os objetos utilizados na entrada e saída de dados da API.
* **Config:** concentra as configurações de segurança e autenticação.
* **Exception / Handler:** centraliza o tratamento de exceções.
* **Enums:** contém valores enumerados utilizados pelas regras da aplicação.

A estrutura do projeto possui essas camadas separadas dentro do pacote principal, enquanto a autenticação utiliza `JwtAuthenticationFilter`, `SecurityConfiguration` e `TokenProvider`.
