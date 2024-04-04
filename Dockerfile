# Используем образ с Java и Maven для сборки приложения
FROM maven:3.8.4-openjdk-17-slim AS build

# Копируем исходный код в контейнер
COPY . /app
WORKDIR /app

# Собираем приложение
RUN mvn clean package -DskipTests

# Используем образ с Java для запуска приложения
FROM openjdk:17-jdk-alpine

# Копируем собранный JAR-файл из предыдущего этапа в новый контейнер
COPY --from=build /app/target/Diplom-0.0.1-SNAPSHOT.jar /app/Diplom-0.0.1-SNAPSHOT.jar

# Указываем порт, который будет открыт в контейнере
EXPOSE 8080

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "/app/Diplom-0.0.1-SNAPSHOT.jar"]