cd catalog
call ./mvnw package
cd ../order
call ./mvnw package
cd ../supplier-gateway
call ./mvnw package
cd ../supplier-notification
call ./mvnw package
cd ..
echo build finalizado...