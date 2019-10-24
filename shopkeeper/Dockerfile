FROM openjdk:8-jdk-alpine
VOLUME /tmp

ARG STOCK_JAR_FILE=stock/target/stock-0.0.1-SNAPSHOT.jar
ARG NOTIFICATION_JAR_FILE=shopkeeper-notification/target/shopkeeper-notification-0.0.1-SNAPSHOT.jar
ARG GATEWAY_JAR_FILE=shopkeeper-gateway/target/shopkeeper-gateway-0.0.1-SNAPSHOT.jar

ARG START_SH_FILE=start.sh

COPY ${STOCK_JAR_FILE} stock.jar
COPY ${NOTIFICATION_JAR_FILE} notification.jar
COPY ${GATEWAY_JAR_FILE} gateway.jar

COPY ${START_SH_FILE} start.sh

RUN apk update && apk add bash

EXPOSE 9090
EXPOSE 9091
EXPOSE 9092

CMD ["/bin/bash", "start.sh"]