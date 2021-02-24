FROM mcr.microsoft.com/java/jre:11-zulu-alpine
COPY /target /usr/app
WORKDIR /usr/app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "riyadh-metro-network-server-0.0.1-milestone.jar"]