FROM alpine:3.8
ARG VERSION
RUN apk --update add openjdk8-jre
RUN mkdir -p /daf/app
COPY ./target/yamlparser-${VERSION}-jar-with-dependencies.jar /daf/daf.jar
WORKDIR /daf/app
CMD ["java", "-jar", "/daf/daf.jar"]
