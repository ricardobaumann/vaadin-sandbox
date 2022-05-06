FROM openjdk:19
WORKDIR /
ADD target/mytodo-1.0-SNAPSHOT.jar app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod app.jar
#./mvnw clean package install -Pproduction && docker build -t blog . && docker run -ti -p 8080:8080 blog