# Projeto: Monitoramento com Spring Boot, Prometheus, Grafana e Docker

## 📝 Descrição do Projeto
[cite_start]Este projeto simula um serviço de e-commerce (`PedidoService`) e demonstra como implementar um sistema de monitoramento completo utilizando Spring Boot Actuator, Micrometer, Prometheus e Grafana [cite: 270-272].

[cite_start]A aplicação expõe métricas técnicas (JVM, HTTP) e métricas de negócio customizadas (valor dos pedidos, total de pedidos criados) [cite: 285-294].

O ambiente de monitoramento (Prometheus e Grafana) é orquestrado via Docker Compose.

## 🛠️ Tecnologias Utilizadas
* Java 17 / Spring Boot
* [cite_start]Spring Boot Actuator (Coleta de Métricas) [cite: 305-308]
* Micrometer (Registro de Métricas)
* [cite_start]Prometheus (Armazenamento de Métricas / Alertas) [cite: 309-312]
* Grafana (Visualização / Dashboards)
* Docker / Docker Compose

---

## 🚀 Como Executar o Ambiente Completo

### Pré-requisitos
* Java 17+ (Maven)
* Docker e Docker Compose

### Passo 1: Iniciar o Stack de Monitoramento (Docker)
Na raiz do projeto (onde estão `docker-compose.yml` e `prometheus.yml`), execute:

```bash
docker-compose up -d
```
Isso iniciará os contêineres do Prometheus e Grafana em background.

* **Prometheus** estará acessível em: `http://localhost:9090`
* **Grafana** estará acessível em: `http://localhost:3000`

### Passo 2: Iniciar a Aplicação Spring Boot
Em um terminal separado, inicie a aplicação `PedidoService`:

```bash
./mvnw spring-boot:run
```
A aplicação estará rodando em `http://localhost:8080`.

### Passo 3: Verificar o Scrape do Prometheus
1.  Acesse o Prometheus: `http://localhost:9090`
2.  Vá em `Status > Targets`.
3.  [cite_start]Você deve ver o job `pedido-service` com o *State* "UP"[cite: 351]. (Pode levar alguns segundos para conectar).

---

## 📊 Configurando o Grafana

1.  **Acesse o Grafana:** `http://localhost:3000`
2.  [cite_start]**Login:** `admin` / `admin` [cite: 357] (você será solicitado a trocar a senha no primeiro login).

### 1. Adicionar o Prometheus como Fonte de Dados (Data Source)
1.  No menu lateral (engrenagem ⚙️), clique em `Data Sources`.
2.  Clique em `Add data source`.
3.  Selecione `Prometheus`.
4.  [cite_start]No campo `URL`, insira: `http://prometheus:9090`[cite: 358].
    *(Usamos `prometheus` como host, pois o Grafana está rodando no Docker e pode acessar o contêiner do Prometheus pelo nome do serviço definido no `docker-compose.yml`).*
5.  Clique em `Save & test`.

### 2. Importar Dashboards Prontos
O Grafana possui dashboards excelentes prontos para JVM e Spring Boot.

1.  No menu lateral (quadrados 🪟), vá em `Dashboards`.
2.  Clique em `New > Import`.
3.  [cite_start]No campo "Import via grafana.com", insira um dos IDs abaixo e clique em `Load`[cite: 359]:
    * **`4701`**: Spring Boot 2.1 JVM Micrometer
    * **`6756`**: Spring Boot Statistics
4.  Na tela seguinte, selecione o *Data Source* do Prometheus que você acabou de criar.
5.  Clique em `Import`.

---

## 🧪 Gerando Métricas para Teste

Use o Postman ou `curl` para enviar pedidos à API e gerar métricas.

```bash
# Simule um pedido de R$ 150,00
curl -X POST http://localhost:8080/pedidos \
-H "Content-Type: application/json" \
-d '{
    "id": 1,
    "descricao": "Produto A",
    "valor": 150.00
}'

# Simule um pedido de R$ 50,00
curl -X POST http://localhost:8080/pedidos \
-H "Content-Type: application/json" \
-d '{
    "id": 2,
    "descricao": "Produto B",
    "valor": 50.00
}'
```
Como o `PedidoController` simula falhas e latência, algumas requisições podem demorar ou falhar (retornando erro 500), o que é ideal para ver os alertas de latência e taxa de erro funcionando nos dashboards.