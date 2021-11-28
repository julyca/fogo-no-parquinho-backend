# fogo-no-parquinho-backend
Este é o GitHub do backend da aplicação: Fogo no Parquinho

## Sobre o projeto
"Fogo no Parquinho" consiste no projeto de avaliações entre alunos e professores com o fim de avaliar o desempenho do profissional, do estudante e das disciplinas do curso oferecido.
Como proposta inicial, todos os usuários podem comentar um sobre os outros: 
- Um aluno pode avaliar um professor, fora e dentro do escopo da disciplina ministrada; 
- Um professor pode avaliar o desempenho de um aluno ou de outros professores, se assim achar pertinente;
- E um aluno pode comentar sobre o desempenho de outros alunos, por exemplo o quão bom é fazer trabalho com o outro.

## Sobre a aplicação
API feita em Kotlin que implementa as funções básicas definidas para o produto "Fogo no Parquinho". Para conexão com o banco de dados está sendo utilizada uma VM com MariaDB, para facilitar na criação do banco de dados bem como sua população na pasta `\data` deste projeto tem disponivel um script SQL para a criação e população da base de dados.

OBS: Vale ressaltar que em caso de erro ao rodar o script como um todo deve ser executado comando por comando.

Para uma melhor visualização da estrutura do banco de dados, foi criado um DER (Diagrama de Entidade e Relacionamento) para ilustrar as tabelas do banco de dados.
![DER Fogo no Parquinho](./data/DER_fogo_no_parquinho.jfif)

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

### GET /users
Listagem de todos os usuários cadastrados na aplicação. (Não Paginada)

Exemplo de retorno:
```
[
    {
        "id" : "0",
        "username" : "agenteP",
        "code" : "00.00000-0",
        "fullName" : "Perry o Ornitorrinco",
        "roleId" : "0"
    }
]
```

### GET /users/roles
Listagem de todos os tipos de perfil de usuário existentes na aplicação. (Não Paginada)

Exemplo de retorno:
```
[
    {
        "id" : "0",
        "roleName" : "professor"
    }
]
```

### GET /users/{userCode}
Retorna os dados de um usuário especifico, com base no código informado.

Exemplo de retorno:
```
{
    "id" : "0",
    "username" : "agenteP",
    "code" : "00.00000-0",
    "fullName" : "Perry o Ornitorrinco",
    "roleId" : "0"
}
```

### GET /users/{userCode}/review
Listagem de todas as avaliações feitas sobre um usuário especifico, com base no código informado. (Não Paginada)

Exemplo de retorno:
```
[
    {
        "reviewId" : "0",
        "score" : "0",
        "feedback" : "odeio muito tudo isso"
        "reviewerName" : "agenteP",
        "reviewerRole" : "0",
        "creationTime" : "2021-11-11"
    }
]
```

### POST /users/{userCode}/review
Registro de nova avaliação feita sobre o usuário identificado (pelo código informado). **REQUER AUTENTICAÇÃO**

Exemplo de JSON Body:
```
{
    "score" : "0",
    "feedback" : "odeio muito tudo isso"
}
```

### POST /users/subject
Registra relação entre usuário e a disciplina identificada (pelo código informado). **REQUER AUTENTICAÇÃO**

O relacionamento deve ser interpretado de acordo com a função/perfil do usuário. Ex:
- Se o usuário for de perfil `professor` -> Usuário leciona Disciplina
- Se o usuário for de perfil `aluno` -> Aluno cursa/cursou Disciplina

Exemplo de JSON Body:
```
{
    "subjectId" : "0"
}
```

### GET /subjects
Listagem de todas as disciplinas cadastradas. (Não Paginada)

Exemplo de retorno:
```
[
    {
        "id" : "0",
        "code" : "ECM251",
        "name" : "Linguagens de Programação I",
        "description" : "Magia de Camponês"
        "creationTime" : "2021-11-11"
    }
]
```

### GET /subjects/{subjectCode}
Retorna os dados de uma matéria especifica, com base no código informado.

Exemplo de retorno:
```
{
    "id" : "0",
    "code" : "ECM251",
    "name" : "Linguagens de Programação I",
    "description" : "Magia de Camponês"
    "creationTime" : "2021-11-11"
}
```

### GET /subjects/{subjectCode}/review
Listagem de todas as avaliações feitas sobre uma matéria especifica, com base no código informado. (Não Paginada)

Exemplo de retorno:
```
[
    {
        "reviewId" : "0",
        "score" : "0",
        "feedback" : "odeio muito tudo isso"
        "reviewerName" : "agenteP",
        "reviewerRole" : "0",
        "creationTime" : "2021-11-11"
    }
]
```

### POST /subjects
Cadastro de nova disciplina na aplicação. **REQUER AUTENTICAÇÃO**

Exemplo de JSON Body:
```
{
    "code" : "ECM251",
    "name" : "Linguagens de Programação I",
    "description" : "Magia de Camponês"
}
```

### POST /subjects/{subjectCode}/review
Registro de nova avaliação feita sobre a disciplina identificada (pelo código informado). **REQUER AUTENTICAÇÃO**

Exemplo de JSON Body:
```
{
    "score" : "0",
    "feedback" : "odeio muito tudo isso"
}
```



