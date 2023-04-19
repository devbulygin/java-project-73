FROM gradle:7.4.0-jdk17

WORKDIR /app

COPY / .

RUN ./gradlew bootRun --args='--spring.profiles.active=prod'

CMD build/install/app/bin/app