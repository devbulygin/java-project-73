FROM gradle:7.2.0-jdk17

WORKDIR /app

COPY ./ .

RUN ./gradlew installDist

CMD ./gradlew installDist
