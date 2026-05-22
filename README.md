# Olli Pet / Clyvo Challenge — DevOps Tools & Cloud Computing

Este repositório contém a entrega da disciplina **DevOps Tools & Cloud Computing**, com uma API Java Spring Boot conteinerizada e executando em nuvem na Azure.

O projeto representa a base operacional da plataforma **Clyvo / Olli Pet**, uma solução de intermediação veterinária inteligente. A ideia é transformar a Clivovet em um verdadeiro “cérebro operacional veterinário”, centralizando informações de tutores, pets, veterinários e prontuários, além de preparar a aplicação para evoluções futuras com IA multiagente, triagem por sintomas, perfil comportamental contínuo do pet, recomendação automática de clínicas e integração com dispositivos IoT.

---

## Link do vídeo da entrega

> **Cole aqui o link do vídeo no YouTube após a gravação:**

```text
LINK_DO_VIDEO_YOUTUBE_AQUI
```

---

## Link do repositório

```text
https://github.com/HenriqueRodriguesV/challenge_clyvo-master
```

---

## Objetivo da entrega

O objetivo desta entrega é demonstrar uma solução Java funcionando em ambiente de nuvem, utilizando Docker e persistência de dados com banco H2 containerizado.

A aplicação foi preparada para atender aos requisitos da atividade:

- CRUD completo com `GET`, `POST`, `PUT` e `DELETE`;
- pelo menos 2 inserts com conteúdo significativo;
- persistência de dados com banco H2;
- aplicação e banco executando em containers Docker;
- uso de Docker Compose;
- volume nomeado para persistência do banco;
- execução da aplicação em background;
- aplicação rodando com usuário sem privilégios administrativos;
- provisionamento de VM Linux na Azure via Azure CLI;
- abertura das portas necessárias ao projeto;
- README com descrição, benefícios, arquitetura, rotas, instalação, Dockerfile, Docker Compose e scripts Azure CLI.

---

## Descrição do projeto

O **Olli Pet / Clyvo Challenge** foi desenvolvido pensando em um cenário real de operação veterinária. A proposta é criar uma plataforma que ajude tutores a encontrarem atendimento adequado para seus pets, ao mesmo tempo em que organiza dados importantes para clínicas, veterinários e gestores.

Na versão atual, o sistema funciona como uma API para cadastro e gerenciamento de:

- responsáveis pelos pets;
- pets;
- veterinários;
- prontuários e atendimentos.

Mesmo sendo uma entrega focada em DevOps, a aplicação foi construída com uma ideia de negócio clara: centralizar dados para que, no futuro, a plataforma possa usar inteligência artificial para interpretar sintomas, entender o comportamento do pet, prever urgências, reduzir abandono de tratamento e indicar a clínica mais adequada conforme localização, especialidade, fila e custo.

Assim, o projeto não é apenas um CRUD simples. Ele é a base técnica para uma solução mais inteligente, escalável e preparada para evoluir.

---

## Benefícios para o negócio

A solução traz benefícios importantes para a Clivovet/Clyvo:

1. **Centralização dos dados**
   - Informações de tutores, pets, veterinários e prontuários ficam organizadas em uma única base.

2. **Melhor rastreabilidade**
   - O histórico de atendimentos pode ser consultado e atualizado com facilidade.

3. **Base para tomada de decisão**
   - Os dados armazenados podem apoiar decisões sobre atendimento, urgência e acompanhamento.

4. **Preparação para IA multiagente**
   - A arquitetura permite evoluir para uma camada de IA capaz de analisar sintomas, comportamento do pet e risco de abandono de tratamento.

5. **Melhor experiência para o tutor**
   - No futuro, a plataforma pode reduzir dúvidas, melhorar encaminhamentos e tornar o atendimento mais rápido.

6. **Escalabilidade operacional**
   - A conteinerização facilita a execução em nuvem e a evolução do projeto.

---

## Arquitetura macro da solução

A solução foi estruturada com foco em separação de responsabilidades:

```text
Usuário / Tutor
      |
      | Acesso pela internet
      v
IP Público da VM Azure
      |
      | Porta 8080
      v
Container da API Java Spring Boot
      |
      | Comunicação TCP interna
      v
Container do Banco H2
      |
      | Persistência
      v
Volume Docker nomeado: clyvo-h2-data
```

### Componentes principais

| Componente | Descrição |
|---|---|
| VM Linux Azure | Ambiente em nuvem onde a solução é executada |
| Docker | Plataforma utilizada para conteinerizar a aplicação |
| Docker Compose | Orquestra os containers da API e do banco |
| API Spring Boot | Camada principal da aplicação |
| H2 Database | Banco de dados escolhido para persistência |
| Volume Docker | Mantém os dados do banco mesmo após restart dos containers |
| Swagger | Interface para testar as rotas da API |
| H2 Console | Interface para visualizar e validar os dados do banco |

Arquivo de apoio para arquitetura:

```text
docs/arquitetura.mmd
```

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Docker
- Docker Compose
- Azure CLI
- VM Linux Ubuntu na Azure
- Swagger / OpenAPI
- Git e GitHub

---

## Entidades do sistema

A aplicação possui quatro entidades principais:

### Responsáveis

Representam os tutores dos pets.

Campos principais:

- id;
- nome;
- email;
- cpf;
- data de nascimento.

### Pets

Representam os animais cadastrados na plataforma.

Campos principais:

- id;
- nome;
- descrição;
- raça;
- data de nascimento;
- responsável vinculado.

### Veterinários

Representam os profissionais que realizam atendimentos.

Campos principais:

- id;
- nome;
- email;
- cpf;
- CRMV.

### Prontuários

Representam os registros de atendimento dos pets.

Campos principais:

- id;
- procedimento;
- data do procedimento;
- local de atendimento;
- pet vinculado;
- veterinário vinculado.

---

## Rotas da API

A documentação Swagger pode ser acessada por:

```text
http://IP_PUBLICO_DA_VM:8080/swagger-ui.html
```

Principais endpoints disponíveis:

### Responsáveis

```text
GET    /responsaveis
GET    /responsaveis/{id}
POST   /responsaveis
PUT    /responsaveis/{id}
DELETE /responsaveis/{id}
```

### Pets

```text
GET    /pets
GET    /pets/{id}
POST   /pets
PUT    /pets/{id}
DELETE /pets/{id}
```

### Veterinários

```text
GET    /veterinarios
GET    /veterinarios/{id}
POST   /veterinarios
PUT    /veterinarios/{id}
DELETE /veterinarios/{id}
```

### Prontuários

```text
GET    /prontuarios
GET    /prontuarios/{id}
POST   /prontuarios
PUT    /prontuarios/{id}
DELETE /prontuarios/{id}
```

---

## Inserts significativos

O projeto possui dados iniciais configurados no arquivo:

```text
src/main/resources/data.sql
```

Esses dados incluem:

- 2 responsáveis;
- 2 veterinários;
- 2 pets;
- 2 prontuários.

Os registros foram criados com conteúdo relacionado ao contexto do projeto, como tutores, pets acompanhados, veterinários e prontuários de atendimento. Isso permite demonstrar a aplicação já funcionando com dados reais de exemplo assim que os containers são iniciados.

---

## Dockerfile da API

A API utiliza um Dockerfile com build multi-stage:

1. uma etapa para compilar o projeto com Maven;
2. uma etapa final apenas para executar o `.jar`.

Na imagem final, a aplicação roda com o usuário:

```text
appuser
```

Isso atende ao requisito de não executar a aplicação com usuário root dentro do container.

Comando usado para validar:

```bash
sudo docker exec clyvo-api whoami
```

Resultado esperado:

```text
appuser
```

---

## Docker Compose

O projeto utiliza Docker Compose para subir a aplicação e o banco em containers separados.

Arquivo:

```text
docker-compose.yml
```

Serviços principais:

| Serviço | Função |
|---|---|
| api | Container da aplicação Java Spring Boot |
| h2-db | Container do banco H2 |

O banco utiliza o volume nomeado:

```text
clyvo-h2-data
```

Esse volume garante que os dados permaneçam salvos mesmo após reiniciar os containers.

Comando usado para validar o volume:

```bash
sudo docker volume ls | grep clyvo
```

Resultado esperado:

```text
clyvo-h2-data
```

---

## Como executar localmente com Docker

Na raiz do projeto, execute:

```bash
docker compose up -d --build
```

Verificar os containers:

```bash
docker compose ps
```

Ver logs da API:

```bash
docker compose logs -f api
```

Parar os containers:

```bash
docker compose down
```

Parar os containers e apagar o volume do banco:

```bash
docker compose down -v
```

---

## Execução em nuvem com Azure CLI

O script principal de criação da infraestrutura está em:

```text
scripts/azure-create-vm.sh
```

Esse script realiza:

1. criação do Resource Group;
2. criação da VM Linux Ubuntu na Azure;
3. abertura das portas necessárias ao projeto:
   - `8080` para a API;
   - `8082` para o H2 Console;
4. instalação de Docker, Docker Compose, Git, Nano e JQ;
5. clone do repositório do projeto;
6. execução da solução em background com Docker Compose.

A porta `22` de SSH é criada automaticamente pela Azure durante o provisionamento da VM.

### Executar o script

```bash
chmod +x scripts/azure-create-vm.sh
./scripts/azure-create-vm.sh
```

Ao final da execução, o script exibe o IP público da VM.

---

## Portas utilizadas

| Porta | Uso |
|---|---|
| 22 | SSH para acessar a VM |
| 8080 | API Spring Boot e Swagger |
| 8082 | H2 Console |
| 9092 | Comunicação TCP do banco H2 |

---

## Acesso à aplicação em nuvem

Após criar a VM e subir os containers, acesse:

### Swagger

```text
http://IP_PUBLICO_DA_VM:8080/swagger-ui.html
```

### H2 Console

```text
http://IP_PUBLICO_DA_VM:8082
```

Dados de conexão no H2 Console:

```text
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:tcp://h2-db:9092/challenge_clyvo
User Name: sa
Password: vazio
```

Se estiver acessando o H2 Console diretamente pelo container do banco, também pode ser usado:

```text
jdbc:h2:tcp://localhost:9092/challenge_clyvo
```

---

## Consultas úteis no H2 Console

Após conectar no H2 Console, execute:

```sql
SHOW TABLES;
```

Depois consulte as tabelas principais:

```sql
SELECT * FROM RESPONSAVEL;
SELECT * FROM PET;
SELECT * FROM VETERINARIO;
SELECT * FROM PRONTUARIO;
```

Essas consultas ajudam a demonstrar no vídeo que os dados realmente estão no banco escolhido.

---

## Testes externos da API

O projeto possui um script para testar as principais operações da API:

```text
scripts/testes-api-curl.sh
```

Execução:

```bash
chmod +x scripts/testes-api-curl.sh
./scripts/testes-api-curl.sh http://IP_PUBLICO_DA_VM:8080
```

Esse script demonstra:

- listagem de responsáveis;
- criação de responsável;
- listagem de veterinários;
- criação de pet;
- atualização de pet;
- remoção de pet;
- acesso ao Swagger e ao H2 Console.

Também é possível testar manualmente com `curl`:

```bash
curl http://IP_PUBLICO_DA_VM:8080/responsaveis
curl http://IP_PUBLICO_DA_VM:8080/pets
curl http://IP_PUBLICO_DA_VM:8080/veterinarios
curl http://IP_PUBLICO_DA_VM:8080/prontuarios
```

---

## Validação de persistência

Para validar que os dados persistem no volume nomeado, foi realizado o seguinte fluxo:

1. criação de um novo responsável via API;
2. restart dos containers;
3. nova consulta na API;
4. confirmação de que o registro continuou salvo.

Exemplo de comando:

```bash
curl -s -X POST http://IP_PUBLICO_DA_VM:8080/responsaveis \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste Persistencia Azure",
    "email": "persistencia.azure@email.com",
    "cpf": "99988877766",
    "dataNascimento": "2000-01-01"
  }'
```

Reiniciar containers:

```bash
sudo docker compose restart
```

Consultar novamente:

```bash
curl http://IP_PUBLICO_DA_VM:8080/responsaveis
```

Registro esperado:

```text
Teste Persistencia Azure
```

---

## Remoção obrigatória dos recursos em nuvem

Ao final da entrega, é obrigatório remover os recursos criados na Azure.

Script de remoção:

```text
scripts/azure-delete-resources.sh
```

Execução:

```bash
chmod +x scripts/azure-delete-resources.sh
./scripts/azure-delete-resources.sh
```

O script remove o Resource Group usado na entrega, apagando a VM e os recursos associados.

Guarde o print da exclusão para anexar no PDF final.

---

## Checklist da entrega

| Requisito | Status |
|---|---|
| CRUD com GET, POST, PUT e DELETE | OK |
| Pelo menos 2 inserts significativos | OK |
| Persistência com banco H2 | OK |
| Banco H2 containerizado | OK |
| Dockerfile da aplicação | OK |
| Docker Compose com app e banco | OK |
| Volume nomeado para persistir dados | OK |
| Aplicação rodando em background | OK |
| Aplicação rodando sem root | OK |
| VM Linux criada na Azure | OK |
| Portas necessárias abertas | OK |
| Docker, Git, Nano e JQ instalados na VM | OK |
| Swagger acessível externamente | OK |
| H2 Console acessível externamente | OK |
| Script Azure CLI de criação | OK |
| Script Azure CLI de remoção | OK |
| README com descrição, benefícios, arquitetura, rotas e instalação | OK |
| Link do vídeo no YouTube | OK |
| PDF final com evidências | OK |
| Print da remoção da VM | OK |

---

## Considerações finais

O projeto demonstra a conteinerização e execução em nuvem de uma API Java Spring Boot com persistência em banco H2. A entrega atende aos requisitos da atividade de DevOps Tools & Cloud Computing, mostrando uma aplicação funcional, documentada, executando em background, com banco em container separado e persistência garantida por volume nomeado.

Além da parte técnica, a solução possui uma proposta de negócio coerente com o desafio da Clyvo/Olli Pet, criando uma base para uma plataforma veterinária inteligente que pode evoluir para recursos de IA, automação e monitoramento preventivo.
