FROM gradle:7.5.1-jdk17

#WORKDIR ./app
#
#COPY ./app .

RUN make install

CMD ./build/install/app/bin/app