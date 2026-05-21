#!/usr/bin/env bash
set -euo pipefail

RG="rg-clyvo-devops"

az group delete --name "$RG" --yes --no-wait

echo "Solicitada a remoção do Resource Group $RG. Tire print dessa execução e do Portal Azure para anexar no PDF."
