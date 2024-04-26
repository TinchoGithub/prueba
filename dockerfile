# Usa una imagen de OpenJDK como base
FROM adoptopenjdk:11-jre-hotspot

# Crea un directorio de la aplicación
WORKDIR /app

# Copia el directorio de compilación Gradle (incluyendo el JAR) al contenedor
COPY build/libs/ms-student.jar /app/ms-student.jar

# Expone el puerto en el que la aplicación se ejecutará en el contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación Spring
CMD ["java", "-jar", "ms-student.jar"]
