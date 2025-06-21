@echo off
echo Subindo banco de dados...
docker-compose up -d

REM Aguarda 10 segundos
ping 127.0.0.1 -n 11 > nul

echo Iniciando aplicação...
call mvnw spring-boot:run