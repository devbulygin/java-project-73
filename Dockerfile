FROM gradle:7.4.0-jdk17

WORKDIR /app

COPY / .

RUN ./gradlew installDist

CMD ./gradlew installDist