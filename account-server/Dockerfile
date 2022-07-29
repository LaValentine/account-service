FROM maven:3.6.3-openjdk-11 as build
RUN mvn package -DskipTests

FROM openjdk:11
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]