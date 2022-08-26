# Spring Security Example

## Basic Auth

Se trata de uma autenticação feita pelo servidor usando um encode a partir do `username` e `password` do usuario

### Status Code

- 401 `Unauthorized`: Não logado e esperando receber as credenciais de autorização
- 403 `Forbidden`: Logado, mas sem autorização para o recurso


## Vulnerabilidades em Sistemas Web

### Autenticação e Autorização

Caso o sistema apenas autentique o usuario, mas não realiza a autorização, pode gerar uma falha de segurança em que os
usuarios consigam acessar dados que ele não possui acesso. Dessa forma a diferença entre a Autorização e Autenticação
seria:

- Autenticação: Obter as credenciais do usuarios, validar o Login e permitir as suas ações
- Autorização: Determina a quais rotas/acessos o usuario possui no sistema

### Bibliotecas Externas

Sempre manter as depedencias atualizadas para que englobe as novas versões com as correções de antigos BUGS.
