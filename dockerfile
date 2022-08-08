FROM java:8
MAINTAINER CCC <2219444584@qq.com>

ADD ./target/Blog-0.0.1-SNAPSHOT.jar /app.jar
CMD java -jar /app.jar --spring.profiles.active=prod > out