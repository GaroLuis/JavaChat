FROM eclipse-temurin:26-jdk AS build
WORKDIR /var/www/javachat
COPY apps/backend .
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:26-jre
RUN mkdir -p /var/www/javachat && chown -R www-data:www-data /var/www/javachat
WORKDIR /var/www/javachat
COPY --from=build --chown=www-data:www-data /var/www/javachat/build/libs/*.jar app.jar
USER www-data
EXPOSE 8080

ENTRYPOINT ["tail", "-f", "/dev/null"]
