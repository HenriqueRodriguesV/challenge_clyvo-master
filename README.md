# Olli Pet / Clyvo Challenge - DevOps Tools & Cloud Computing

API Java Spring Boot criada para a entrega de DevOps Tools & Cloud Computing. A solução representa a base operacional da plataforma Clyvo/Olli Pet, uma intermediação veterinária inteligente que conecta tutores, pets, veterinários e prontuários, com visão futura de IA multiagente, triagem por sintomas, digital twin comportamental do pet, recomendação automática de clínica e integração com IoT.

## Benefícios para o negócio

A solução transforma a Clivovet em um cérebro operacional veterinário. Em vez de apenas intermediar contatos, a plataforma centraliza dados dos tutores, pets, veterinários e atendimentos, permitindo rastreabilidade, histórico clínico, recomendação de atendimento e melhor tomada de decisão. Com a evolução para IA multiagente, a empresa poderá identificar urgências, reduzir abandono de tratamento, priorizar casos críticos e encaminhar o tutor para a clínica mais adequada com base em especialidade, localização, fila e custo.

## Arquitetura macro

Fluxo principal:

1. Tutor acessa a API pela internet.
2. API Spring Boot roda em container Docker dentro de uma VM Linux na Azure.
3. API executa CRUD de responsáveis, pets, veterinários e prontuários.
4. Banco H2 roda em container separado, acessado via TCP.
5. Os dados são persistidos em volume nomeado Docker `clyvo-h2-data`.
6. A camada conceitual de IA multiagente analisa sintomas, perfil comportamental do pet e recomenda atendimento.

Arquivo de apoio: `docs/arquitetura.mmd`.

## Rotas da API

Documentação Swagger:

```text
http://IP_PUBLICO_DA_VM:8080/swagger-ui.html
```

Console H2:

```text
http://IP_PUBLICO_DA_VM:8082
```

JDBC URL do H2 usado pela aplicação:

```text
jdbc:h2:tcp://h2-db:9092/challenge_clyvo
```

Principais endpoints:

```text
GET    /responsaveis
GET    /responsaveis/{id}
POST   /responsaveis
PUT    /responsaveis/{id}
DELETE /responsaveis/{id}

GET    /pets
GET    /pets/{id}
POST   /pets
PUT    /pets/{id}
DELETE /pets/{id}

GET    /veterinarios
GET    /veterinarios/{id}
POST   /veterinarios
PUT    /veterinarios/{id}
DELETE /veterinarios/{id}

GET    /prontuarios
GET    /prontuarios/{id}
POST   /prontuarios
PUT    /prontuarios/{id}
DELETE /prontuarios/{id}
```

## Inserts significativos

O projeto contém o arquivo `src/main/resources/data.sql`, que cria dados iniciais para demonstração:

- 2 responsáveis;
- 2 veterinários;
- 2 pets;
- 2 prontuários.

Esses dados servem para demonstrar a persistência no banco H2 e as operações de leitura, atualização e remoção.

## Como executar localmente com Docker

```bash
docker compose up -d --build
```

Verificar containers:

```bash
docker compose ps
```

Ver logs da API:

```bash
docker compose logs -f api
```

Parar a solução:

```bash
docker compose down
```

Parar e apagar também o volume do banco:

```bash
docker compose down -v
```

## Testes externos da API

Após subir a aplicação, execute:

```bash
./scripts/testes-api-curl.sh http://IP_PUBLICO_DA_VM:8080
```

Também é possível testar pelo Swagger:

```text
http://IP_PUBLICO_DA_VM:8080/swagger-ui.html
```

## Dockerfile

A API usa build multi-stage com Maven e Java 21. Na imagem final, a aplicação roda com usuário sem privilégios administrativos (`appuser`), cumprindo o requisito de não executar a aplicação como root.

## Docker Compose

O `docker-compose.yml` sobe dois serviços:

- `api`: aplicação Java Spring Boot;
- `h2-db`: banco H2 em modo TCP.

O banco utiliza o volume nomeado abaixo para persistência:

```text
clyvo-h2-data
```

## Script Azure CLI

O script principal está em:

```text
scripts/azure-create-vm.sh
```

Ele realiza:

1. criação do Resource Group;
2. criação da VM Linux Ubuntu;
3. abertura das portas 22, 8080 e 8082;
4. instalação de Docker, Docker Compose, Git e Nano;
5. clone do repositório;
6. execução da solução com `docker compose up -d --build`.

Antes de executar, altere a variável abaixo dentro do script:

```bash
REPO_URL="COLE_AQUI_O_LINK_DO_SEU_REPOSITORIO_GITHUB"
```

Execução:

```bash
chmod +x scripts/azure-create-vm.sh
./scripts/azure-create-vm.sh
```

## Remoção obrigatória da VM

Ao final da entrega, delete os recursos criados na Azure:

```bash
chmod +x scripts/azure-delete-resources.sh
./scripts/azure-delete-resources.sh
```

Guarde o print da exclusão para anexar no PDF final.

## Checklist da entrega

- [x] CRUD com GET, POST, PUT e DELETE.
- [x] Pelo menos 2 inserts com conteúdo significativo.
- [x] Banco H2 containerizado.
- [x] Dockerfile da aplicação.
- [x] Docker Compose com app e banco.
- [x] Volume nomeado para persistir os dados do banco.
- [x] Aplicação em background com `docker compose up -d`.
- [x] Aplicação rodando com usuário sem privilégios administrativos.
- [x] Script Azure CLI para provisionar VM Linux.
- [x] Abertura das portas necessárias ao projeto.
- [x] Instalação de Docker, Git e Nano na VM.
- [x] README com descrição, benefícios, arquitetura, rotas e instalação.
- [ ] Print da aplicação rodando na Azure.
- [ ] Print do banco mostrando as operações executadas.
- [ ] Link do vídeo no YouTube.
- [ ] Print da exclusão da VM e recursos em nuvem.
