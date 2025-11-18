# Sistema Acadêmico (Backend)

## 1. Descrição do Projeto
[cite_start]Este projeto é o backend de um sistema acadêmico full stack, desenvolvido com Spring Boot 3[cite: 1, 11]. [cite_start]O objetivo é simular um sistema acadêmico com foco em relacionamento entre entidades [cite: 4][cite_start], segurança [cite: 5][cite_start], monitoramento [cite: 6][cite_start], testes [cite: 7] [cite_start]e documentação de API[cite: 8].

[cite_start]A API gerencia o cadastro de Alunos e Cursos, permitindo o relacionamento N:N entre eles[cite: 76, 79].

## 2. Tecnologias Utilizadas
* [cite_start]**Backend:** Spring Boot 3 [cite: 11]
* [cite_start]**Web:** Spring Boot Starter Web [cite: 14]
* [cite_start]**Banco de Dados:** Spring Data JPA [cite: 16] [cite_start]e H2 Database [cite: 19]
* [cite_start]**Segurança:** Spring Security [cite: 15]
* [cite_start]**Monitoramento:** Spring Boot Actuator [cite: 17, 31][cite_start], Prometheus [cite: 32] [cite_start]e Grafana [cite: 34]
* [cite_start]**Documentação:** Springdoc (Swagger) [cite: 18]
* [cite_start]**Testes:** JMeter (para Carga e Stress) [cite: 51] e JUnit (implícito no Spring)
* **Build:** Maven

## 3. Como Rodar Localmente
1.  Certifique-se de ter o Java 17+ e o Maven instalados.
2.  Clone este repositório.
3.  Navegue até a pasta `backend` (onde está o `pom.xml`).
4.  Execute o comando:
    ```bash
    # Para Windows (PowerShell)
    .\mvnw.cmd spring-boot:run
    
    # Para Linux/Mac
    ./mvnw spring-boot:run
    ```
5.  A aplicação estará disponível em `http://localhost:8080`.

## 4. Como Acessar Swagger e Autenticação
Esta é a parte mais importante para testar a API e popular seu frontend.

* **API (Swagger):** `http://localhost:8080/swagger-ui.html`
* **Banco (H2):** `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:academicodb` (Use esta URL para conectar)

### Guia de Teste no Swagger (Passo a Passo)

Para criar um aluno e vê-lo no frontend, siga esta ordem:

**Passo 1: Autorizar**
1.  No Swagger, clique no botão **"Authorize"** no canto superior direito.
2.  Digite `user` e `password` (definidos no `SecurityConfig.java`) e clique em "Authorize".
3.  Feche a janela modal. Você está autenticado.

**Passo 2: Criar um Curso**
1.  Vá em `CursoController` > `POST /api/cursos`.
2.  Clique em **"Try it out"**.
3.  No `Request body`, insira o JSON do curso:
    ```json
    {
      "nome": "Ciência da Computação",
      "cargaHoraria": 4000
    }
    ```
4.  Clique **"Execute"**. Se a resposta for `200`, anote o `id` do curso criado (ex: `1`).

**Passo 3: Criar um Aluno (Evitando o Erro)**
1.  Vá em `AlunoController` > `POST /api/alunos`.
2.  Clique em **"Try it out"**.
3.  **IMPORTANTE:** O Swagger vai sugerir um JSON que inclui `cursos` com `id: 0`. Se você enviar isso, receberá um **Erro 500 (Internal Server Error)**, pois o sistema tentará encontrar um curso com `id 0`, que não existe.
4.  **CORREÇÃO:** Apague a chave `cursos` do JSON. O `Request body` deve conter **apenas** os dados do aluno:
    ```json
    {
      "nome": "Seu Nome Aqui",
      "email": "teste@email.com",
      "matricula": "12345"
    }
    ```
5.  Clique **"Execute"**. Anote o `id` do aluno criado (ex: `1`).

**Passo 4: Matricular o Aluno no Curso**
1.  Vá em `AlunoController` > `POST /api/alunos/{alunoId}/matricular/{cursoId}`.
2.  Clique em **"Try it out"**.
3.  Preencha o `alunoId` (ex: 1) e o `cursoId` (ex: 1) que você anotou.
4.  Clique **"Execute"**.

Pronto! Agora, se você atualizar seu frontend, o aluno aparecerá na lista.

## 5. Como Configurar Prometheus e Grafana
[cite_start]O monitoramento é configurado via Docker[cite: 88].

1.  Certifique-se de que o Docker esteja em execução.
2.  Na raiz do projeto (`avaliacao02`), onde está o `docker-compose.yml`, execute:
    ```bash
    docker-compose up -d
    ```
3.  Acesse os serviços:
    * [cite_start]**Prometheus:** `http://localhost:9090` [cite: 97] [cite_start](Verifique em "Status" > "Targets" se o `spring-boot-app` está "UP" [cite: 110]).
    * [cite_start]**Grafana:** `http://localhost:3000` [cite: 101] (Login padrão: admin/admin).

## 6. Como Executar Testes de Carga (JMeter)
[cite_start]Para avaliar o desempenho da API[cite: 48]:

1.  [cite_start]Abra o Apache JMeter[cite: 51].
2.  Crie um "Grupo de Usuários" (Thread Group).
3.  Configure o número de usuários (threads) e o tempo de simulação.
4.  Adicione uma "Requisição HTTP" (HTTP Request) para `GET http://localhost:8080/api/alunos`.
5.  Adicione um "Gerenciador de Autorização HTTP" (HTTP Authorization Manager) com as credenciais `user` e `password`.
6.  [cite_start]Adicione "Listeners" (ex: "Relatório Sumário") para analisar os resultados[cite: 53].
7.  Execute o teste.

## 7. Como Fazer Deploy no Render
1.  Faça o push do seu código para um repositório no GitHub.
2.  [cite_start]Crie uma conta no Render [cite: 68] e selecione "New" -> "Web Service".
3.  Conecte seu repositório GitHub.
4.  Configure as seguintes opções (aponte para a pasta `backend` se o `pom.xml` estiver lá):
    * **Runtime:** `Java`
    * **Build Command:** `.\mvnw.cmd clean install`
    * **Start Command:** `java -jar target/sistema-academico-0.0.1-SNAPSHOT.jar`
5.  Clique em "Create Web Service".

## 8. Referências Utilizadas
* [cite_start]**Spring Boot:** `https://docs.spring.io/spring-boot/docs/current/reference/html` [cite: 21, 22]
* [cite_start]**Spring Data JPA:** `https://docs.spring.io/spring-data/jpa/docs/current/reference/html` [cite: 23, 24]
* [cite_start]**Spring Security:** `https://docs.spring.io/spring-security/reference/index.html` [cite: 25, 26, 44, 45]
* [cite_start]**Swagger (Springdoc):** `https://springdoc.org` [cite: 27]
* [cite_start]**Actuator:** `https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html` [cite: 36, 37]
* [cite_start]**Prometheus:** `https://prometheus.io/docs/introduction/overview` [cite: 38]
* [cite_start]**Grafana:** `https://grafana.com/docs/grafana/latest` [cite: 39]
* [cite_start]**JMeter:** `https://jmeter.apache.org/usermanual/index.html` [cite: 51]
* [cite_start]**Render:** `https://render.com/docs/deploy-spring` [cite: 68]