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

---

# Roadmap

## Nível 1 — Completar a API

* [ ] Implementar CRUD completo para os recursos existentes
* [ ] Padronizar respostas de erro
* [ ] Implementar filtros e ordenação
* [ ] Aprimorar documentação com OpenAPI/Swagger

## Nível 2 — Controle de Estoque

* [ ] Implementar entrada de estoque
* [ ] Implementar saída de estoque
* [ ] Implementar transferência entre localizações
* [ ] Implementar controle de estoque mínimo
* [ ] Implementar consulta de estoque baixo/crítico
* [ ] Implementar histórico de movimentações
* [ ] Garantir operações de estoque com controle transacional
* [ ] Implementar controle de concorrência

## Nível 3 — Fluxo Operacional

* [ ] Criar sistema de requisição de materiais
* [ ] Implementar aprovação de requisições
* [ ] Implementar rejeição de requisições
* [ ] Implementar separação de materiais
* [ ] Implementar atendimento parcial de requisições
* [ ] Integrar requisições com entregas
* [ ] Criar cadastro de fornecedores
* [ ] Criar sistema de compras
* [ ] Implementar recebimento de materiais
* [ ] Integrar recebimento com entrada de estoque

## Nível 4 — Segurança e Governança

* [ ] Implementar controle de acesso baseado em roles (RBAC)
* [ ] Criar roles `ADMIN`, `ALMOXARIFE`, `SUPERVISOR` e `USUARIO`
* [ ] Configurar permissões por operação
* [ ] Implementar auditoria de operações
* [ ] Registrar usuário responsável pelas alterações
* [ ] Registrar data e hora das operações
* [ ] Implementar controle de acesso aos recursos

## Nível 5 — Qualidade e Testes

* [ ] Criar testes unitários
* [ ] Criar testes para Services
* [ ] Criar testes para Controllers
* [ ] Criar testes para Repositories
* [ ] Criar testes de integração
* [ ] Criar testes de autenticação e autorização
* [ ] Implementar Testcontainers
* [ ] Testar integração com MySQL
* [ ] Criar testes para regras de estoque
* [ ] Criar testes de concorrência

## Nível 6 — Banco de Dados e Persistência

* [ ] Implementar Flyway
* [ ] Criar migrations para o banco de dados
* [ ] Versionar alterações do schema
* [ ] Revisar índices do banco
* [ ] Revisar relacionamentos e constraints
* [ ] Implementar Specifications para filtros dinâmicos

## Nível 7 — Produção e Infraestrutura

* [ ] Criar `application-dev.yml`
* [ ] Criar `application-test.yml`
* [ ] Criar `application-prod.yml`
* [ ] Remover credenciais do código-fonte
* [ ] Configurar variáveis de ambiente
* [ ] Implementar Docker
* [ ] Criar Docker Compose
* [ ] Containerizar aplicação e banco de dados
* [ ] Criar pipeline de CI/CD
* [ ] Automatizar build e testes
* [ ] Automatizar criação da imagem Docker
* [ ] Configurar deploy

## Nível 8 — Observabilidade

* [ ] Implementar Spring Boot Actuator
* [ ] Criar endpoint de health check
* [ ] Implementar métricas da aplicação
* [ ] Padronizar logs
* [ ] Implementar logs estruturados
* [ ] Monitorar requisições HTTP
* [ ] Monitorar erros e exceções
* [ ] Monitorar conexão com o banco
* [ ] Integrar Micrometer
* [ ] Integrar Prometheus
* [ ] Criar dashboards com Grafana

## Nível 9 — Relatórios

* [ ] Criar relatório de estoque
* [ ] Criar relatório de entradas
* [ ] Criar relatório de saídas
* [ ] Criar relatório de movimentações
* [ ] Criar relatório de consumo
* [ ] Criar relatório de peças mais utilizadas
* [ ] Implementar filtros por período
* [ ] Implementar exportação CSV
* [ ] Implementar exportação Excel
* [ ] Implementar exportação PDF

## Nível 10 — Recursos Avançados

* [ ] Implementar Redis para cache
* [ ] Criar sistema de notificações
* [ ] Implementar alertas de estoque baixo
* [ ] Criar dashboard administrativo
* [ ] Criar frontend para consumo da API
* [ ] Implementar controle de indicadores do almoxarifado
* [ ] Implementar análise de consumo
* [ ] Implementar previsão de necessidade de reposição
