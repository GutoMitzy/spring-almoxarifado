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

### Autenticação

| Método | Endpoint                         | Operação                            |
| ------ | -------------------------------- | ----------------------------------- |
| `POST` | `/v2/almoxarifado/auth/register` | Registra um novo usuário            |
| `POST` | `/v2/almoxarifado/auth/login`    | Realiza login e retorna o token JWT |

O login retorna um `TokenResponseDto`.

### Categorias

| Método | Endpoint                      | Operação                    |
| ------ | ----------------------------- | --------------------------- |
| `POST` | `/v2/almoxarifado/categorias` | Cadastra uma nova categoria |

### Empresas

| Método | Endpoint                    | Operação                  |
| ------ | --------------------------- | ------------------------- |
| `POST` | `/v2/almoxarifado/empresas` | Cadastra uma nova empresa |

### Corredores

| Método | Endpoint                                                   | Operação                               |
| ------ | ---------------------------------------------------------- | -------------------------------------- |
| `POST` | `/v2/almoxarifado/corredores`                              | Cadastra um novo corredor              |
| `GET`  | `/v2/almoxarifado/corredores/{id}?page={page}&size={size}` | Consulta um corredor de forma paginada |

### Itens

| Método | Endpoint                                                     | Operação                            |
| ------ | ------------------------------------------------------------ | ----------------------------------- |
| `POST` | `/v2/almoxarifado/itens`                                     | Cadastra um novo item               |
| `GET`  | `/v2/almoxarifado/itens?page={page}&size={size}`             | Lista os itens de forma paginada    |
| `GET`  | `/v2/almoxarifado/itens/{categoria}?page={page}&size={size}` | Lista itens filtrados por categoria |

### Entrada de Estoque

| Método  | Endpoint                                | Operação                        |
| ------- | --------------------------------------- | ------------------------------- |
| `POST`  | `/v2/almoxarifado/entradas`             | Registra uma entrada de estoque |
| `PATCH` | `/v2/almoxarifado/entradas/{id}/finish` | Finaliza uma entrada de estoque |

## Dependências principais

| Tecnologia              | Finalidade                              |
| ----------------------- | --------------------------------------- |
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

As versões de Java, Spring Boot, SpringDoc, JJWT, H2 e demais dependências são definidas atualmente no `pom.xml`.

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
DATABASE_NAME=almoxarifado
DATABASE_DEV=almoxarifado_dev
DATABASE_USERNAME**
DATABASE_PASSWORD=**

JWT_KEY=**
JWT_EXPIRATION=**
```

| Variável            | Descrição                                              |
| ------------------- | ------------------------------------------------------ |
| `DATABASE_NAME`     | Nome do banco principal                                |
| `DATABASE_DEV`      | Nome do banco utilizado no ambiente de desenvolvimento |
| `DATABASE_USERNAME` | Usuário do banco                                       |
| `DATABASE_PASSWORD` | Senha do banco                                         |
| `JWT_KEY`           | Chave utilizada para assinatura dos tokens JWT         |
| `JWT_EXPIRATION`    | Tempo de expiração do JWT em milissegundos             |

> O arquivo `.env` deve ser utilizado apenas para configurações locais. Não versione credenciais reais no repositório.

### Observação sobre o `.env`

O `.env` é carregado pelo **Docker Compose** para substituir as variáveis `${...}` presentes no `docker-compose.yaml`.

Ele não é automaticamente carregado pelo Spring Boot durante a execução direta pela IDE.

Para execução local pelo IntelliJ, as variáveis devem ser configuradas no ambiente de execução da aplicação.

## Docker

O projeto possui um `Dockerfile` para construção da imagem da aplicação e um `docker-compose.yaml` para orquestrar a aplicação e os bancos de dados.

A configuração possui três serviços:

| Serviço                         | Container          | Porta          |
| ------------------------------- | ------------------ | -------------- |
| Aplicação Spring Boot           | `almoxarifado-app` | `8082`         |
| MySQL principal                 | `mysql`            | `3306` interno |
| MySQL de desenvolvimento/testes | `mysql-test`       | `3307:3306`    |

A aplicação aguarda o `mysql_db` ficar saudável antes de iniciar o container `backend_core`.

## Executando com Docker

Primeiramente, crie o arquivo `.env` na raiz do projeto.

Depois, execute:

Para executar os containers em segundo plano:

```bash
docker compose up -d --build
```

## Executando em desenvolvimento

O projeto possui um profile `dev` configurado em `application-dev.yaml`.

Nesse ambiente, a aplicação utiliza:

```text
MySQL: localhost:3307
Banco: ${DATABASE_DEV}
Porta da API: 8080
```

Com o profile `dev` ativo:

```text
SPRING_PROFILES_ACTIVE=dev
```

A configuração atual de `application-dev.yaml` utiliza `localhost:3307` para conexão com o banco de desenvolvimento.

## Documentação da API

A aplicação utiliza **SpringDoc OpenAPI** para documentação da API.

Com a aplicação executando, a interface do Swagger pode ser acessada através de:

```text
http://localhost:8082/swagger-ui/index.html
```

A especificação OpenAPI está disponível em:

```text
/v3/api-docs
```
