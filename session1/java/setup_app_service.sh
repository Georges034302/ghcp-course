#!/bin/bash

# ==== CONFIGURE THESE VARIABLES ====
RG_NAME="my-ghcp-demo-rg"
LOCATION="australiaeast"
PLAN_NAME="my-ghcp-demo-plan"
APP_NAME="my-ghcp-demo-app-$(openssl rand -hex 3)"   # Must be globally unique
GH_SECRET_NAME="AZURE_WEBAPP_PUBLISH_PROFILE"

# ==== LOGIN TO AZURE ====
echo "🔐 Logging in to Azure..."
az login

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
  --runtime "JAVA|21-java11"

echo "✅ Your Azure Web App Name is: $APP_NAME"

echo "⬇️ Downloading publish profile..."
az webapp deployment list-publishing-profiles \
  --name $APP_NAME \
  --resource-group $RG_NAME \
  --output xml > PublishProfile.xml

echo "📄 Downloaded PublishProfile.xml"

echo "🔑 Uploading publish profile as GitHub secret: $GH_SECRET_NAME..."
gh secret set $GH_SECRET_NAME < PublishProfile.xml

echo "✅ Set GitHub secret: $GH_SECRET_NAME"

echo "🧹 Cleaning up local publish profile..."
rm PublishProfile.xml

echo "🎉 Done! Your Azure resources are ready and your GitHub secret is set."
echo "👉 Use app-name: $APP_NAME in your GitHub Actions workflow."
