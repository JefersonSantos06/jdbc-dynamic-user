# Projeto de Viabilidade: Migracao para JdbcTemplate + Repository

## Objetivo

Este projeto existe para validar, de forma pratica, a viabilidade de migrar a estrutura atual de outro sistema para um modelo baseado em:

- `Spring JDBC` (`JdbcClient` / `NamedParameterJdbcOperations`)
- camada de `Repository` para acesso a dados

## Stack Tecnologica

- Java 25
- Spring Boot 4
- Vaadin 25
- Spring Security
- Spring JDBC (`spring-boot-starter-jdbc`)
- Flyway (migracoes de banco)
- H2 (ambiente de desenvolvimento)
- Tomcat JDBC Pool

## Arquitetura Aplicada

### Camadas principais

- `views`: interface Vaadin (`TesteView`)
- `services`: regras de negocio (`UsuarioService`)
- `repository`: SQL e persistencia (`UsuarioRepository`, `UsuarioRepositoryImpl`)
- `entities`: entidades de dominio (`Usuario`)
- `config`: seguranca e datasource (`SecurityConfig`, `DatasourceConfiguration`)

### Estrategia de acesso a dados

- O acesso ao banco e feito por repositories concretos, sem ORM.
- O `UsuarioRepositoryImpl` concentra SQL de busca, insert e update.
- `AbstractRepository` fornece infraestrutura comum para `JdbcClient` e `NamedParameterJdbcOperations`.

### Seguranca e conexao

- Login com `Spring Security` (form login e basic auth).
- `UserDetailsService` consulta usuario via `UsuarioService`.
- `DatasourceConfiguration` usa um datasource primario que tenta abrir conexao com credenciais dinamicas baseadas no usuario autenticado.
