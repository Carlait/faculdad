Sistema Monolítico para Gerenciamento de Funcionários e Departamentos

Descrição do projeto

Este projeto é uma aplicação monolítica construída com Spring Boot para o gerenciamento completo de funcionários e seus respectivos departamentos. O sistema oferece uma API REST para operações de CRUD (Criar, Ler, Atualizar, Deletar) e uma interface web interativa desenvolvida com Thymeleaf para a visualização e manipulação dos dados.

A aplicação utiliza MariaDB como banco de dados para o ambiente de produção e H2 para testes, garantindo a persistência dos dados de forma eficiente e organizada.

Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias e dependências:

    Backend:

        Spring Boot

        Spring Web (API REST + MVC)

        Spring Data JPA

        Lombok

        Spring DevTools

    Frontend:

        Thymeleaf

    Banco de Dados:

        MariaDB (Produção)

        H2 (Testes/Provas)

Estrutura do Banco de Dados

O banco de dados é composto por duas entidades principais:

Entidade: Departamento

    id (Long): Chave primária.

    nome (String)

    localizacao (String)

Entidade: Funcionário

    id (Long): Chave primária.

    nome (String)

    email (String)

    dataAdmissao (LocalDate)

    departamento: Relacionamento @ManyToOne com a entidade Departamento.

Passo a passo para executar a aplicação

    Clone o repositório:
    Bash

git clone https://github.com/Carlait/faculdad.git
cd seu-repositorio

Configuração do Banco de Dados
Por padrão, o projeto está configurado para iniciar com o banco de dados em memória H2 (perfil test), que não exige nenhuma configuração manual.

(Opcional) Para usar em produção com MariaDB, remova a linha spring.profiles.active=test do application.properties e configure suas credenciais do MariaDB.

Execute a aplicação:
Utilize o Maven Wrapper para executar o projeto.
Bash

    ./mvnw spring-boot:run

    Acesse a aplicação:

        Interface Web: Abra seu navegador e acesse http://localhost:8080/web/
        Novo Funcionário: http://localhost:8080/web/funcionarios/novo
        Lista de Funcionários: http://localhost:8080/web/funcionarios
        Novo Departamento: http://localhost:8080/web/departamentos/novo
        Lista de Departamentos: http://localhost:8080/web/departamentos

        Console H2 (para testes): Para visualizar o banco de dados em memória, acesse http://localhost:8080/h2-console. Utilize as credenciais definidas em application-test.properties.

Exemplos de requisições (API REST)

Você pode usar ferramentas como Postman ou curl para interagir com a API REST.

Departamentos

    Cadastrar um novo departamento:
    Bash

curl -X POST http://localhost:8080/api/departamentos \
-H "Content-Type: application/json" \
-d '{"nome": "Recursos Humanos", "localizacao": "Bloco A"}'

Listar todos os departamentos:
Bash

curl -X GET http://localhost:8080/api/departamentos

Buscar departamento por ID:
Bash

curl -X GET http://localhost:8080/api/departamentos/1

Atualizar um departamento:
Bash

curl -X PUT http://localhost:8080/api/departamentos/1 \
-H "Content-Type: application/json" \
-d '{"nome": "RH", "localizacao": "Bloco C"}'

Excluir um departamento:
Bash

    curl -X DELETE http://localhost:8080/api/departamentos/1

Funcionários

    Cadastrar um novo funcionário (vinculado a um departamento):
    Bash

curl -X POST http://localhost:8080/api/funcionarios \
-H "Content-Type: application/json" \
-d '{"nome": "Ana Silva", "email": "ana.silva@example.com", "dataAdmissao": "2024-01-15", "departamento": {"id": 1}}'

Listar todos os funcionários:
Bash

curl -X GET http://localhost:8080/api/funcionarios

Buscar funcionário por ID:
Bash

curl -X GET http://localhost:8080/api/funcionarios/1

Excluir um funcionário:
Bash

    curl -X DELETE http://localhost:8080/api/funcionarios/1

Imagens da interface Thymeleaf

A interface web oferece uma navegação simples para listar, criar, editar e visualizar os dados de departamentos e funcionários.

### Página Inicial
![Tela inicial da aplicação](images/home.png)

### Departamentos
**Tela de listagem de todos os departamentos**
![Tela de listagem de todos os departamentos](images/departamentos.png)

**Formulário para adicionar um novo departamento**
![Formulário para adicionar um novo departamento](images/novosdepartamentos.png)

### Funcionários
**Tela de listagem de todos os funcionários**
![Tela de listagem de todos os funcionários](images/funcionarios.png)

**Formulário para adicionar um novo funcionário**
![Formulário para adicionar um novo funcionário](images/novosfuncionarios.png)