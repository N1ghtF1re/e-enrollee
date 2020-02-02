FROM gradle:4.7.0-jdk8-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon


FROM tomcat:9-jre8-alpine

COPY --from=build /home/gradle/src/build/libs/e-enrollment-1.0.war  /usr/local/tomcat/webapps/enrollee.war

EXPOSE 8080

CMD ["catalina.sh", "run"]