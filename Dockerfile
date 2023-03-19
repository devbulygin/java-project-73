FROM gradle:7.2.0-jdk16

WORKDIR /app

COPY ./ .

RUN make start

CMD build/install/app/bin/app