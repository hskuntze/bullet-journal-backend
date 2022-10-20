FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/bullet-journal-0.0.1-SNAPSHOT.jar bullet-journal.jar
ENTRYPOINT ["java", "-jar", "/bullet-journal.jar"]