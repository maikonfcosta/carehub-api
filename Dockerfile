# 1. Fase de Build: Usamos a imagem oficial do Maven e Java 21 (ou a versão que você escolheu)
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml e faz o download das dependências. Isso otimiza o cache do Docker.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo o código-fonte
COPY src /app/src

# Constrói o projeto e gera o arquivo JAR
RUN mvn clean package -DskipTests

# 2. Fase de Execução: Usamos uma imagem base leve (apenas Java Runtime Environment)
FROM eclipse-temurin:21-jre-alpine

# Define o argumento JAR_FILE (o nome do arquivo JAR gerado)
ARG JAR_FILE=target/*.jar

# Copia o JAR da fase de build para a fase de execução
COPY --from=build /app/${JAR_FILE} app.jar

# Expõe a porta que o Spring Boot usa (8080 por padrão)
EXPOSE 8080

# Comando para rodar a aplicação quando o contêiner iniciar
# Ativamos o profile de produção ('prod') que configuramos no application.yml
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]