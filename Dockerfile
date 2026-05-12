# ==========================================
# Etapa 1: Construcción (Build Stage)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamos el pom.xml primero para aprovechar el caché de capas de Docker
COPY pom.xml .
# Descargamos las dependencias offline para acelerar futuras construcciones
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto omitiendo los tests para agilizar el despliegue
RUN mvn clean package -DskipTests

# ==========================================
# Etapa 2: Ejecución (Runtime Stage)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos únicamente el archivo .jar generado desde la Etapa 1
# Nota: Asumimos el nombre generado por tu pom.xml (Negocio-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/Negocio-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que está corriendo Tomcat (según tus logs)
EXPOSE 8093

# Configuramos variables de entorno para optimizar el uso de RAM en contenedores
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Punto de entrada de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]