# Projeto: API CRUD com Testes Automatizados (Mockito + JMeter)

## 📝 Descrição do Projeto
Esta é uma API RESTful desenvolvida em Java com Spring Boot que realiza operações CRUD (Create, Read, Update, Delete) para uma entidade `Produto`.

O projeto segue uma arquitetura em camadas (Controller, Service, Repository) e utiliza um banco de dados H2 em memória.

O foco principal deste projeto é a aplicação de testes automatizados:
* [cite_start]**Testes Unitários:** Utilizando **Mockito** para testar a camada de serviço (Service), simulando o comportamento da camada de repositório (Repository) [cite: 52-54].
* [cite_start]**Testes de Desempenho:** Utilizando **Apache JMeter** para simular cargas de acesso e analisar o desempenho dos endpoints[cite: 56].

## 🛠️ Tecnologias Utilizadas

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database (Banco em memória)
* Lombok
* Mockito (para testes unitários)
* Apache JMeter (para testes de desempenho)

## 🚀 Instruções para Executar a Aplicação

1.  **Pré-requisitos:**
    * Java JDK 17 (ou superior)
    * Maven

2.  **Clonar o Repositório:**
    ```bash
    git clone [URL-DO-SEU-REPOSITORIO]
    cd crud-testes
    ```

3.  **Executar a Aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Acesso à API:**
    A aplicação estará disponível em `http://localhost:8080`.

5.  **Acesso ao Console H2:**
    Você pode acessar o banco de dados em memória através do navegador:
    * URL: `http://localhost:8080/h2-console`
    * JDBC URL: `jdbc:h2:mem:testdb`
    * User Name: `sa`
    * Password: (deixe em branco)

## 🧪 Como Rodar os Testes Unitários (Mockito)

Os testes unitários da camada de serviço já estão implementados em `src/test/java`.

Para executá-los, utilize o seguinte comando Maven na raiz do projeto:

```bash
./mvnw test
```

Um relatório de cobertura de testes (se configurado com JaCoCo) ou os resultados serão exibidos no console.

## 📈 Como Executar os Testes de Desempenho (JMeter)

Os testes de desempenho são executados com o Apache JMeter e o plano de teste (`.jmx`) incluído neste repositório.

1.  **Baixar o Apache JMeter:**
    * Faça o download no site oficial: [jmeter.apache.org](https://jmeter.apache.org/download_jmeter.cgi)
    * Descompacte o arquivo e navegue até a pasta `bin`.

2.  **Abrir o Plano de Teste:**
    * Execute o JMeter (ex: `jmeter.bat` no Windows ou `jmeter.sh` no Linux/Mac).
    * [cite_start]No JMeter, clique em `File > Open` e selecione o arquivo `plano-de-teste.jmx` (incluído no repositório)[cite: 71].

3.  **Configurar os "Listeners" (Relatórios):**
    O plano de teste já deve conter os principais relatórios. Caso não, adicione-os:
    * Clique com o botão direito em `Thread Group > Add > Listener`:
        * [cite_start]`Summary Report` [cite: 59]
        * [cite_start]`Aggregate Report` [cite: 61]
        * [cite_start]`View Results Tree` [cite: 63]

4.  **Executar o Teste:**
    * Certifique-se de que a aplicação Spring Boot (`http://localhost:8080`) esteja em execução.
    * No JMeter, clique no botão "Start" (Play verde).

5.  **Analisar os Relatórios:**
    Após a execução, analise os *Listeners* para verificar:
    * **Summary Report:** Tempo médio de resposta, vazão (throughput) e taxa de erro[cite: 59].
    * **Aggregate Report:** Estatísticas detalhadas por endpoint (média, mediana, 90% Line, etc.)[cite: 61].
    * **View Results Tree:** Resposta individual de cada requisição (útil para depurar erros)[cite: 63].

### 📊 Exemplo de Relatórios JMeter (Capturas de Tela)

*(Adicione aqui as capturas de tela dos seus relatórios Summary e Aggregate após executar os testes)*





## 📦 Exemplos de Requisições (curl/Postman)

### 1. Criar Produto (POST)
`POST /produtos`
```json
{
  "nome": "Smartphone",
  "descricao": "Smartphone 5G",
  "preco": 2500.00
}
```

### 2. Listar Todos (GET)
`GET /produtos`

### 3. Buscar por ID (GET)
`GET /produtos/1`

### 4. Atualizar Produto (PUT)
`PUT /produtos/1`
```json
{
  "nome": "Smartphone Plus",
  "descricao": "Smartphone 5G com 256GB",
  "preco": 2800.00
}
```

### 5. Excluir Produto (DELETE)
`DELETE /produtos/1`