# start.sh
java -jar catalog.jar &
java -jar order.jar &
java -jar notification.jar &
java -jar gateway.jar