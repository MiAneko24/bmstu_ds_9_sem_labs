FROM gradle:7.6.0-jdk17-jammy

WORKDIR /home/gradle/application

COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY gradle.properties ./
COPY src ./src

#FROM base as test
#RUN gradle test
#
#FROM base as build

RUN gradle bootJar
#CMD tail -f /dev/null
CMD java -jar build/libs/paymentService-0.0.1-SNAPSHOT-all.jar
