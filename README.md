# Spring Security Example

- `@EnableWebSecurity`: Sobrescreve as Configurações padrões do Spring Security pelas Configurações criadas na Classe

## Basic Auth

Se trata de uma autenticação feita pelo servidor usando um encode a partir do `username` e `password` do usuario

## Vulnerabilidades em Sistemas Web

### Autenticação e Autorização

Caso o sistema apenas autentique o usuario, mas não realiza a autorização, pode gerar uma falha de segurança em que os
usuarios consigam acessar dados que ele não possui acesso. Dessa forma a diferença entre a Autorização e Autenticação
seria:

- Autenticação: Obter as credenciais do usuarios, validar o Login e permitir as suas ações
    - Quando falhar, será apresentado o **Status Code** 401 `Unauthorized`: Não logado e esperando receber as
      credenciais de autorização
- Autorização: Determina a quais rotas/acessos o usuario possui no sistema
    - Quando falhar, será apresentado o **Status Code** 403 `Forbidden`: Logado, mas sem autorização para o recurso
    - Por padrão no `Spring Security`, não é suficiente estar autenticado para realizar requesições `POST`/`DELETE`.
      Isso ocorre por conta da segurança contra `CSFR` habilitada por padrão.

### Bibliotecas Externas

Sempre manter as depedencias atualizadas para que englobe as novas versões com as correções de antigos BUGS.

## Referencias

- Instalações e Dependencias
    - [Spring Doc - Swagger](https://springdoc.org)
    - ["Hello Word" com Spring Doc](https://www.javainuse.com/spring/boot_swagger3)
    - [Spring Doc com Spring Security](https://www.javainuse.com/spring/boot_swaggersec)
- Guias e Tutoriais
    - [MICHELLI BRITO: Curso Spring Security - Basic Auth](https://www.youtube.com/watch?v=t6prPki7daU)
    - [DANIELE LEÃO: Autenticação e Autorização - Basic Auth](https://www.youtube.com/watch?v=pkIEV2Yls_8)
