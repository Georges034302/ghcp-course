#!/bin/bash

# Load environment variables from .env
set -a
source .env
set +a

# Check ADMIN_TOKEN is set
if [[ -z "$ADMIN_TOKEN" ]]; then
  echo "❌ ADMIN_TOKEN not set in .env!"
  exit 1
fi

# ==== CONFIGURE THESE VARIABLES ====
RG_NAME="ghcp-demo-rg"
LOCATION="australiaeast"
PLAN_NAME="ghcp-demo-plan"
APP_NAME="java-sb-api-app-$(openssl rand -hex 3)"   # Must be globally unique
GH_SECRET_NAME="AZURE_WEBAPP_PUBLISH_PROFILE"

# ==== LOGIN TO AZURE ====
# echo "🔐 Logging in to Azure..."
# az login

echo "🌐 Registering Microsoft.Web provider (if needed)..."
az provider register --namespace Microsoft.Web

echo "📦 Creating resource group: $RG_NAME in $LOCATION..."
az group create --name $RG_NAME --location $LOCATION

echo "🛠️ Creating App Service plan: $PLAN_NAME..."
az appservice plan create \
  --name $PLAN_NAME \
  --resource-group $RG_NAME \
  --sku B1 \
  --is-linux

echo "☁️ Creating Web App: $APP_NAME (Java 21, Linux)..."
az webapp create \
  --resource-group $RG_NAME \
  --plan $PLAN_NAME \
  --name $APP_NAME \
  --runtime "JAVA|21-java21"

echo "✅ Your Azure Web App Name is: $APP_NAME"

echo "⬇️ Downloading publish profile..."
az webapp deployment list-publishing-profiles \
  --name $APP_NAME \
  --resource-group $RG_NAME \
  --output xml > PublishProfile.xml

echo "📄 Downloaded PublishProfile.xml"

# ==== Authenticate GitHub CLI with admin token ====

echo "🔑 Clearing interfering environment variables..."
unset GH_TOKEN
unset GITHUB_TOKEN

echo "🔐 Logging out any existing GitHub CLI session..."
gh auth logout --hostname github.com || true

echo "🔑 Logging in to GitHub CLI with admin PAT from .env..."
echo "$ADMIN_TOKEN" | gh auth login --with-token --hostname github.com

# Verify authentication success
if gh auth status --hostname github.com --show-token &>/dev/null; then
  echo "✅ gh CLI authenticated for github.com!"
else
  echo "❌ gh CLI authentication failed!"
  exit 1
fi

echo "🔑 Uploading publish profile as GitHub secret: $GH_SECRET_NAME..."
gh secret set $GH_SECRET_NAME < PublishProfile.xml

echo "✅ Set GitHub secret: $GH_SECRET_NAME"

echo "🔑 Uploading app name as GitHub secret: APP_NAME..."
echo "$APP_NAME" | gh secret set APP_NAME

echo "✅ Set GitHub secrets: $GH_SECRET_NAME and APP_NAME"

echo "🧹 Cleaning up local publish profile..."
rm PublishProfile.xml

echo "🎉 Done! Your Azure resources are ready and your GitHub secret is set."
echo "👉 Use app-name: $APP_NAME in your GitHub Actions workflow."
