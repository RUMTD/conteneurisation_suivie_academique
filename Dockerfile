# Utiliser une image Java comme base
FROM eclipse-temurin:17-jdk-alpine AS build

# Définir le répertoire de travail
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]