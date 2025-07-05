#!/bin/bash

set -euo pipefail
IFS=$'\n\t'

echo "🔑 Loading environment variables from .env..."
set -a
source .env
set +a

echo "⚙️  Setting up Azure resource variables..."
SP_NAME="github-actions-sp"
RG_NAME="ghcp-demo-rg"
LOCATION="${LOCATION:-australiaeast}"
PLAN_NAME="ghcp-demo-plan"
APP_NAME="java-sb-api-app-$(openssl rand -hex 3)"

echo "🔎 Getting Azure subscription and tenant IDs..."
SUBSCRIPTION_ID=$(az account show --query id -o tsv)
TENANT_ID=$(az account show --query tenantId -o tsv)

echo "🔐 Checking for existing service principal: $SP_NAME"
APP_ID=$(az ad sp list --display-name "$SP_NAME" --query "[0].appId" -o tsv || true)
if [[ -z "$APP_ID" ]]; then
  echo "🆕 Creating new Azure AD app and service principal..."
  APP_ID=$(az ad app create --display-name "$SP_NAME" --query appId -o tsv)
  az ad sp create --id "$APP_ID" > /dev/null
else
  echo "✅ Found existing service principal."
fi

echo "🔑 Ensuring Contributor role assignment on the subscription..."
az role assignment create --assignee "$APP_ID" --role Contributor --scope "/subscriptions/$SUBSCRIPTION_ID" || \
echo "ℹ️  Contributor role may already be assigned."

echo "🔑 Resetting service principal credentials..."
CLIENT_SECRET=$(az ad app credential reset --id "$APP_ID" --append --query password -o tsv)

echo "🌐 Registering Microsoft.Web provider (if needed)..."
az provider register --namespace Microsoft.Web --output none

echo "📦 Creating resource group: $RG_NAME in $LOCATION..."
az group create --name "$RG_NAME" --location "$LOCATION" --output none

echo "🛠️  Creating App Service plan: $PLAN_NAME..."
az appservice plan create --name "$PLAN_NAME" --resource-group "$RG_NAME" --sku B1 --is-linux --output none

echo "☁️  Creating Web App: $APP_NAME (Java 21, Linux)..."
az webapp create --resource-group "$RG_NAME" --plan "$PLAN_NAME" --name "$APP_NAME" --runtime "JAVA|21-java21" --output none

echo "🔒 Generating Azure credentials JSON..."
AZURE_CREDENTIALS=$(cat <<EOF
{
  "clientId": "$APP_ID",
  "clientSecret": "$CLIENT_SECRET",
  "subscriptionId": "$SUBSCRIPTION_ID",
  "tenantId": "$TENANT_ID",
  "activeDirectoryEndpointUrl": "https://login.microsoftonline.com",
  "resourceManagerEndpointUrl": "https://management.azure.com/",
  "activeDirectoryGraphResourceId": "https://graph.windows.net/",
  "sqlManagementEndpointUrl": "https://management.core.windows.net:8443/",
  "galleryEndpointUrl": "https://gallery.azure.com/",
  "managementEndpointUrl": "https://management.core.windows.net/"
}
EOF
)

echo "🔑 Unsetting any existing GH_TOKEN and GITHUB_TOKEN environment variables..."
unset GH_TOKEN GITHUB_TOKEN

echo "🔐 Logging out any existing GitHub CLI session..."
gh auth logout --hostname github.com || true

echo "🔑 Logging in to GitHub CLI with admin PAT from .env..."
echo "$ADMIN_TOKEN" | gh auth login --with-token --hostname github.com

echo "🔑 Uploading publish profile as GitHub secret: AZURE_WEBAPP_PUBLISH_PROFILE..."
gh secret set AZURE_WEBAPP_PUBLISH_PROFILE < PublishProfile.xml

echo "🔑 Uploading app name as GitHub secret: APP_NAME..."
echo "$APP_NAME" | gh secret set APP_NAME

echo "🔑 Uploading Azure credentials as GitHub secret: AZURE_CREDENTIALS..."
echo "$AZURE_CREDENTIALS" | gh secret set AZURE_CREDENTIALS

echo "🧹 Cleaning up local publish profile..."
rm PublishProfile.xml

echo "🎉 Done! Your Azure resources are ready and your GitHub secrets are set."
echo "👉 Use app-name: $APP_NAME in your workflows to refer to this app."
