#!/usr/bin/env bash
set -euo pipefail

RG="rg-clyvo-henrique"
LOCATION="northcentralus"
VM_NAME="vm-clyvo-henrique"
ADMIN_USER="azureuser"
IMAGE="Ubuntu2204"
SIZE="Standard_B2ats_v2"
REPO_URL="https://github.com/HenriqueRodriguesV/challenge_clyvo-master"

az group create --name "$RG" --location "$LOCATION"

az vm create \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --image "$IMAGE" \
  --size "$SIZE" \
  --admin-username "$ADMIN_USER" \
  --generate-ssh-keys \
  --public-ip-sku Standard

az vm open-port --resource-group "$RG" --name "$VM_NAME" --port 22 --priority 1000
az vm open-port --resource-group "$RG" --name "$VM_NAME" --port 8080 --priority 1010
az vm open-port --resource-group "$RG" --name "$VM_NAME" --port 8082 --priority 1020

az vm run-command invoke \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --command-id RunShellScript \
  --scripts "
    set -e
    sudo apt-get update
    sudo apt-get install -y ca-certificates curl gnupg git nano
    sudo install -m 0755 -d /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    sudo chmod a+r /etc/apt/keyrings/docker.gpg
    echo \"deb [arch=\$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \$(. /etc/os-release && echo \$VERSION_CODENAME) stable\" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    sudo usermod -aG docker $ADMIN_USER
    cd /home/$ADMIN_USER
    if [ ! -d challenge-clyvo ]; then git clone $REPO_URL challenge-clyvo; fi
    cd challenge-clyvo
    sudo docker compose up -d --build
    sudo docker compose ps
  "

az vm show -d --resource-group "$RG" --name "$VM_NAME" --query publicIps -o tsv
