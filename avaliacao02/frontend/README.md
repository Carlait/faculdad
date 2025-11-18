# Sistema Acadêmico (Frontend)

[cite_start]Este é o projeto de frontend para o Sistema Acadêmico[cite: 55], desenvolvido como bônus para consumir a API backend Spring Boot.

## 1. Tecnologias Utilizadas
* [cite_start]**React** [cite: 64] [cite_start](usando **Vite** [cite: 65])
* Javascript (ES6+)
* CSS Básico
* `fetch` API para chamadas HTTP

## 2. Como Rodar Localmente
1.  Certifique-se de que o **backend** (API) esteja em execução em `http://localhost:8080`.
2.  Navegue até a pasta `/frontend`.
3.  Instale as dependências:
    ```bash
    npm install
    ```
4.  Execute o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```
5.  Abra `http://localhost:5173` no seu navegador.

## 3. Como Consumir a API
O projeto está configurado para consumir a API do backend.

* [cite_start]**Autenticação:** A API do backend é protegida[cite: 60]. O `App.jsx` usa Autenticação Básica (HTTP Basic Auth) para se comunicar. As credenciais (ex: `user`/`password`) são codificadas em Base64 e enviadas no cabeçalho `Authorization` de cada requisição `fetch`.

* **CORS (Cross-Origin Resource Sharing):**
    Durante o desenvolvimento, o navegador bloqueará requisições de `localhost:5173` (frontend) para `localhost:8080` (backend). Para resolver isso, o arquivo `vite.config.js` está configurado com um **proxy**.

    Qualquer chamada `fetch('/api/...')` (como `/api/alunos`) será automaticamente redirecionada pelo Vite para `http://localhost:8080/api/...`, evitando erros de CORS.

## 4. Como Fazer Deploy no Vercel
1.  Faça o push do seu código para um repositório no GitHub.
2.  [cite_start]Crie uma conta no Vercel [cite: 69] e faça login.
3.  Clique em "Add New..." -> "Project".
4.  Importe o seu repositório no GitHub.
5.  A Vercel deve detectar automaticamente que é um projeto **Vite**.
6.  **Configure as Variáveis de Ambiente:**
    * Você deve adicionar uma variável de ambiente para a URL da sua API de backend (que está hospedada no Render).
    * **Nome:** `VITE_API_URL`
    * **Valor:** `https_url_do_seu_backend_no_render.com`
    * *(Lembre-se de alterar seu código React para usar `import.meta.env.VITE_API_URL` ao fazer as chamadas `fetch` no ambiente de produção)*.
7.  Clique em "Deploy".
8.  [cite_start]A documentação oficial do Vercel pode ser encontrada aqui: `https://vercel.com/docs`[cite: 70].