FROM java:8
VOLUME /tmp
ADD wechat-mall-eureka-8200-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch/app.jar'
EXPOSE 8200
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]