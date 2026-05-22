#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${1:-http://localhost:8080}"

echo "1) Listar responsáveis"
curl -s "$BASE_URL/responsaveis" | jq . || true

echo "2) Criar responsável"
curl -s -X POST "$BASE_URL/responsaveis" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Beatriz Lima",
    "email": "beatriz.lima@email.com",
    "cpf": "56789012345",
    "dataNascimento": "1998-06-12"
  }' | jq . || true

echo "3) Listar veterinários"
curl -s "$BASE_URL/veterinarios" | jq . || true

echo "4) Criar pet"
curl -s -X POST "$BASE_URL/pets" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Luna",
    "descricao": "Pet com monitoramento preventivo por perfil comportamental",
    "raca": "SRD",
    "dataNascimento": "2022-01-20",
    "responsavelId": 1
  }' | jq . || true

echo "5) Atualizar pet 1"
curl -s -X PUT "$BASE_URL/pets/1" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Thor",
    "descricao": "Cachorro acompanhado pela triagem inteligente da Clyvo",
    "raca": "Golden Retriever",
    "dataNascimento": "2021-03-15",
    "responsavelId": 1
  }' | jq . || true

echo "6) Deletar pet 3, caso exista"
curl -s -X DELETE "$BASE_URL/pets/3" -i || true

echo "7) Acessos úteis"
echo "$BASE_URL/swagger-ui.html"
echo "$BASE_URL/h2-console"