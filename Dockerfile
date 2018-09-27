FROM alpine:3.8
RUN apk --update add openjdk8-jre
RUN mkdir -p /daf/app
COPY ./target/yamlparser-0.1.0-jar-with-dependencies.jar /daf/daf.jar
WORKDIR /daf/app
CMD ["java", "-jar", "/daf/daf.jar"]
