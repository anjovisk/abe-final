FROM openjdk:8-jdk-alpine
VOLUME /tmp

ARG CATALOG_JAR_FILE=catalog/target/catalog-0.0.1-SNAPSHOT.jar
ARG ORDER_JAR_FILE=order/target/order-0.0.1-SNAPSHOT.jar
ARG NOTIFICATION_JAR_FILE=supplier-notification/target/supplier-notification-0.0.1-SNAPSHOT.jar
ARG GATEWAY_JAR_FILE=supplier-gateway/target/supplier-gateway-0.0.1-SNAPSHOT.jar

ARG START_SH_FILE=start.sh

COPY ${CATALOG_JAR_FILE} catalog.jar
COPY ${ORDER_JAR_FILE} order.jar
COPY ${NOTIFICATION_JAR_FILE} notification.jar
COPY ${GATEWAY_JAR_FILE} gateway.jar

COPY ${START_SH_FILE} start.sh

RUN apk update && apk add bash

EXPOSE 8080
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083

CMD ["/bin/bash", "start.sh"]