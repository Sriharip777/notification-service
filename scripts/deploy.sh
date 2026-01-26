#!/bin/bash

echo "🚀 Building Notification Service..."

# Step 1: Build JAR
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "❌ Maven build failed"
  exit 1
fi

echo "✅ JAR built successfully"

# Step 2: Build Docker image
docker build -t notification-service:latest .

if [ $? -ne 0 ]; then
  echo "❌ Docker build failed"
  exit 1
fi

echo "✅ Docker image built"

# Step 3: Deploy to Kubernetes
kubectl apply -f k8s/notification_deployment.yaml

if [ $? -ne 0 ]; then
  echo "❌ Kubernetes deployment failed"
  exit 1
fi

echo "🎉 Notification Service deployed successfully"
