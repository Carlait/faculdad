# Projeto: API Segura com Spring Boot, OAuth2 e Keycloak

## 📝 Descrição do Projeto
[cite_start]Esta é uma API RESTful desenvolvida em Java com Spring Boot que demonstra a implementação de segurança moderna usando **Spring Security 6**, **OAuth2** e **Keycloak** como um servidor de autorização [cite: 1, 26-28].

O objetivo é proteger endpoints com base em *roles* (perfis de usuário) gerenciados externamente pelo Keycloak. A API expõe três endpoints para demonstrar diferentes níveis de acesso:
* `/public`: Acesso livre.
* `/user`: Acesso restrito a usuários autenticados (qualquer perfil).
* [cite_start]`/admin`: Acesso restrito a usuários com o perfil "admin"[cite: 55, 66, 70, 74].

## 🛠️ Tecnologias Utilizadas
* Java 17+
* Spring Boot 3+
* Spring Security 6
* Spring OAuth2 Resource Server
* Keycloak (Servidor de Autorização)
* Maven

---

## 🔐 1. Passos para Configurar o Keycloak

[cite_start]Antes de executar a aplicação Spring Boot, o Keycloak **precisa** estar em execução e configurado [cite: 10-21].

### 1.1. Iniciar o Keycloak
1.  [cite_start]Baixe o Keycloak (versão 21+ recomendada)[cite: 7].
2.  Descompacte e inicie o servidor em modo de desenvolvimento:
    ```bash
    # (Dentro da pasta do Keycloak)
    ./bin/kc.sh start-dev
    ```
3.  Acesse o console de administração em `http://localhost:8080` e crie um usuário administrador (ex: `admin`/`admin`).

### 1.2. Criar o Realm
1.  Faça login no console de administração.
2.  No canto superior esquerdo (onde está escrito "master"), clique em "Create Realm".
3.  [cite_start]Nome do Realm: `demo`[cite: 13, 47].
4.  Clique em "Create".

### 1.3. Criar o Client (spring-api)
1.  No menu à esquerda (com o realm "demo" selecionado), clique em "Clients" e "Create client".
2.  [cite_start]**Client ID:** `spring-api` [cite: 14]
3.  [cite_start]**Client authentication:** `On` (Isso equivale ao "Access Type: confidential" [cite: 16]).
4.  [cite_start]Deixe "Standard flow" e "Direct access grant" marcados[cite: 18, 19].
5.  Clique em "Save".
6.  Na aba "Credentials" do client `spring-api`, anote o **Client secret**.

### 1.4. Criar Roles (Perfis)
1.  No menu à esquerda, clique em "Realm Roles" e "Create role".
2.  [cite_start]**Role name:** `admin`[cite: 20]. Salve.
3.  [cite_start]Crie outra role: `user`[cite: 20]. Salve.

### 1.5. Criar Usuários e Atribuir Roles
1.  No menu à esquerda, clique em "Users" e "Add user".
2.  **Username:** `admin_user`. Clique em "Create".
3.  Na aba "Credentials" deste usuário, defina uma senha (ex: `admin`) e desmarque "Temporary".
4.  Na aba "Role mapping", clique em "Assign role", filtre e atribua a role `admin`.

5.  Repita o processo para um usuário comum:
    * **Username:** `normal_user`.
    * **Senha:** `user`.
    * **Role mapping:** `user`.

---

## 🚀 2. Como Rodar a Aplicação Spring Boot

1.  **Clone o repositório:**
    ```bash
    git clone [URL-DO-SEU-REPOSITORIO]
    cd demo-keycloak
    ```

2.  **Verifique o `application.yml`:**
    Confirme que o `issuer-uri` corresponde ao seu realm do Keycloak[cite: 46, 47].
    ```yaml
    spring:
      security:
        oauth2:
          resourceserver:
            jwt:
              issuer-uri: http://localhost:8080/realms/demo
    ```

3.  **Execute a aplicação (com o Keycloak já rodando):**
    ```bash
    ./mvnw spring-boot:run
    ```

---

## 🧪 3. Como Testar os Endpoints

[cite_start]Para testar os endpoints seguros, você primeiro precisa obter um token JWT do Keycloak[cite: 79].

### 3.1. Obter um Token JWT (Ex: Usuário Admin)

Use `curl` ou Postman para fazer uma requisição `POST` ao endpoint de token do Keycloak.

```bash
# Lembre-se de usar o "Client ID" (spring-api) e o "Client Secret" (passo 1.3)
# Troque <CLIENT_SECRET> pelo secret copiado do Keycloak
curl -L -X POST 'http://localhost:8080/realms/demo/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=spring-api' \
--data-urlencode 'client_secret=<CLIENT_SECRET>' \
--data-urlencode 'username=admin_user' \
--data-urlencode 'password=admin' \
--data-urlencode 'grant_type=password'
```

**Resposta (JSON):**
```json
{
    "access_token": "eyJhbGciOiJSUz... (este é o seu token)",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "...",
    "token_type": "Bearer",
    ...
}
```

### 3.2. Testar os Endpoints

[cite_start]Copie o valor de `access_token` e use-o no *header* `Authorization`[cite: 81].

**1. Teste /public (Não precisa de token)**
```bash
curl http://localhost:9090/public
```
*Resposta Esperada:* `Acesso público`

**2. Teste /user (Precisa de token - Admin ou User)**
```bash
# Substitua <TOKEN> pelo access_token
curl -H "Authorization: Bearer <TOKEN>" http://localhost:9090/user
```
*Resposta Esperada:* `Acesso autenticado`

**3. Teste /admin (Precisa de token de Admin)**
```bash
# Use o token obtido com 'admin_user'
curl -H "Authorization: Bearer <TOKEN>" http://localhost:9090/admin
```
*Resposta Esperada:* `Acesso restrito a admins`

**4. Teste de Falha (Acessar /admin com token de 'normal_user')**
*Se você obter um token para o `normal_user` e tentar o comando acima, a API retornará um erro `403 Forbidden`.*