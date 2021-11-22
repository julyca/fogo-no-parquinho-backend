# fogo-no-parquinho-backend
Este é o GitHub do backend da aplicação: Fogo no Parquinho

## Sobre o projeto
"Fogo no Parquinho" consiste no projeto de avaliações entre alunos e professores com o fim de avaliar o desempenho do profissional, do estudante e das disciplinas do curso oferecido.
Como proposta inicial, todos os usuários podem comentar um sobre os outros: 
- Um aluno pode avaliar um professor, fora e dentro do escopo da disciplina ministrada; 
- Um professor pode avaliar o desempenho de um aluno ou de outros professores, se assim achar pertinente;
- E um aluno pode comentar sobre o desempenho de outros alunos, por exemplo o quão bom é fazer trabalho com o outro.

## Sobre a aplicação
API feita em Kotlin que implementa as funções básicas definidas para o produto "Fogo no Parquinho". Para conexão com o banco de dados está sendo utilizada uma VM com MariaDB, para facilitar na criação do banco de dados bem como sua população na pasta `\database` deste projeto tem disponivel um script SQL para a criação e população da base de dados.

OBS: Vale ressaltar que em caso de erro ao rodar o script como um todo deve ser executado comando por comando.

### Consumindo a API

### POST /register
Cadastro do Usuário no sistema.

Exemplo de JSON Body:
```
{
    "username":"agenteP",
    "password":"72d1b93c50e610a71d7fa0ab6df898e0f90efdf7a3858c3be0f5dc03c0473b9c",
    "code":"00.00000-0",
    "fullName":"Perry o Ornitorrinco",
    "roleId":"0"
}
```

### POST /login
Login do Usuário, permitindo o seu acesso a zonas protegidas da API.

Exemplo de JSON Body:
```
{
    "username" : "agenteP",
    "password" : "72d1b93c50e610a71d7fa0ab6df898e0f90efdf7a3858c3be0f5dc03c0473b9c"
}
```

Exemplo do retorno Token JWT HS256:
```
{
    "token" : "tokenJWT"
}
```

#### GET /users
Listagem de todos os usuários cadastrados na aplicação (Não Paginada)

