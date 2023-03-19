FROM gradle:7.2.0-jdk16

WORKDIR /app

COPY ./ .

RUN gradle run

CMD build/install/app/bin/app