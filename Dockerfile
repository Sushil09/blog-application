FROM eclipse-temurin:20

LABEL maintainer="sjsushil09@gmail.com"

WORKDIR /app

COPY target/blog-application-0.0.1-SNAPSHOT.jar /app/springboot-blog-application.jar

ENTRYPOINT ["java", "-jar", "springboot-blog-application.jar"]