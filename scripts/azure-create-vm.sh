cat > scripts/azure-create-vm.sh <<'EOF'
#!/usr/bin/env bash
set -euo pipefail

RG="rg-clyvo-henrique"
LOCATION="northcentralus"
VM_NAME="vm-clyvo-henrique"
ADMIN_USER="azureuser"
IMAGE="Ubuntu2204"
SIZE="Standard_D2s_v3"
REPO_URL="https://github.com/HenriqueRodriguesV/challenge_clyvo-master.git"

echo "Criando Resource Group..."
az group create \
  --name "$RG" \
  --location "$LOCATION"

echo "Criando VM Linux..."
az vm create \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --image "$IMAGE" \
  --size "$SIZE" \
  --admin-username "$ADMIN_USER" \
  --generate-ssh-keys \
  --public-ip-sku Standard

echo "Abrindo portas necessárias ao projeto..."
az vm open-port \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --port 8080 \
  --priority 1010

az vm open-port \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --port 8082 \
  --priority 1020

echo "Instalando Docker, Git, Nano, JQ e ferramentas na VM..."
az vm run-command invoke \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --command-id RunShellScript \
  --scripts "
set -e

sudo apt-get update
sudo apt-get install -y ca-certificates curl gnupg git nano jq

sudo install -m 0755 -d /etc/apt/keyrings

if [ ! -f /etc/apt/keyrings/docker.gpg ]; then
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
fi

sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \"deb [arch=\$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \$(. /etc/os-release && echo \$VERSION_CODENAME) stable\" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo usermod -aG docker $ADMIN_USER

cd /home/$ADMIN_USER

if [ ! -d challenge-clyvo ]; then
  git clone $REPO_URL challenge-clyvo
fi

cd challenge-clyvo

sudo docker compose down || true
sudo docker compose up -d --build
sudo docker compose ps
"

echo "IP público da VM:"
az vm show \
  --resource-group "$RG" \
  --name "$VM_NAME" \
  --show-details \
  --query publicIps \
  -o tsv
EOF