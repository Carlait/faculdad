# Projeto: API de Documentos com Swagger e Deploy no Render

## 📝 Descrição do Projeto
[cite_start]Esta é uma API RESTful desenvolvida em Java com Spring Boot que gerencia **Documentos** e suas respectivas **Categorias**[cite: 49].

A aplicação implementa um CRUD completo para ambas as entidades, utilizando Spring Data JPA para persistência de dados. [cite_start]O projeto também inclui o **Springdoc (Swagger)** para a documentação interativa da API[cite: 53].

[cite_start]O objetivo final desta prática é hospedar a aplicação na plataforma de nuvem **Render**[cite: 55].

## 🛠️ Tecnologias Utilizadas
* Java 17 / Spring Boot
* Spring Web
* Spring Data JPA
* Springdoc OpenAPI (Swagger)
* H2 Database (para ambiente local)
* PostgreSQL (para ambiente de produção no Render)
* Lombok
* Maven

---

## 🚀 1. Link da Aplicação no Render

A aplicação está hospedada na plataforma Render e pode ser acessada através do link abaixo:

**Link da API:** `https://doc-api.onrender.com` 
[cite_start]*(Nota: Substitua pelo link real do seu deploy no Render)* [cite: 56-57]

---

## 📖 2. Documentação Swagger

A documentação completa da API, gerada automaticamente pelo Swagger, está disponível no endpoint `/swagger-ui.html` da aplicação hospedada.

**Link do Swagger:** `https://doc-api.onrender.com/swagger-ui.html`
[cite_start]*(Nota: Substitua pelo link real do seu deploy no Render)* [cite: 73-74]

### Como Usar o Swagger
Ao acessar o link acima, você verá uma interface interativa onde pode:
1.  Visualizar todos os endpoints disponíveis (para Categorias e Documentos).
2.  Ver os modelos de dados (schemas) esperados para `POST` e `PUT`.
3.  Testar cada endpoint diretamente pelo navegador clicando em "Try it out".

---

## 💻 3. Passos para Execução Local

### Pré-requisitos
* Java JDK 17+
* Maven

### Execução
1.  **Clone o repositório:**
    ```bash
    git clone [URL-DO-SEU-REPOSITORIO]
    cd doc-api
    ```

2.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```
    A aplicação usará o banco em memória H2 por padrão localmente.

### Acessos Locais
* **API:** `http://localhost:8080`
* **Console H2:** `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:testdb`
    * **User Name:** `sa`
    * **Password:** (deixe em branco)
* **Swagger UI (Local):** `http://localhost:8080/swagger-ui.html`

---

## ☁️ 4. Instruções de Deploy no Render (Resumo)

Para hospedar esta aplicação no Render:

1.  **Crie uma conta** no [Render](https://render.com).
2.  **Crie um "New PostgreSQL"**:
    * Guarde a "Internal Connection String" (String de Conexão Interna).
3.  **Crie um "New Web Service"**:
    * Conecte seu repositório GitHub.
    * **Build Command:** `./mvnw clean install`
    * **Start Command:** `java -jar target/doc-api-0.0.1-SNAPSHOT.jar`
    * **Environment Variables (Variáveis de Ambiente):**
        * Crie a variável `SPRING_DATASOURCE_URL` e cole a "Internal Connection String" do seu banco PostgreSQL.
        * Adicione `SPRING_JPA_HIBERNATE_DDL_AUTO` com o valor `update`.
        * Adicione `SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT` com o valor `org.hibernate.dialect.PostgreSQLDialect`.
4.  Clique em "Create Web Service" e aguarde o deploy.