#!/usr/bin/env bash
set -euo pipefail

RG="rg-clyvo-henrique"

echo "Solicitando remoção do Resource Group: $RG"

az group delete \
  --name "$RG" \
  --yes \
  --no-wait

echo "Remoção solicitada. Tire print deste terminal e depois confirme no Portal Azure que os recursos foram removidos."