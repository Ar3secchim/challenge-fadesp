#!/bin/bash

# Subir banco via Docker
echo "🔄 Subindo banco de dados..."
docker-compose up -d

# Aguardar banco iniciar
sleep 15

# Executar aplicação Spring Boot
echo "🚀 Iniciando aplicação..."
./mvnw spring-boot:run