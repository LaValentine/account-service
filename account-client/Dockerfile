FROM maven:3.6.3-openjdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
COPY test-client.xml /home/app
RUN mvn -f /home/app/pom.xml package

FROM openjdk:11
COPY --from=build /home/app/test-client.xml /test-client.xml
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]