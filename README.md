# abe-final
Arquitetura de backend e microsserviços

📦abe-final  
 ┣ 📂shopkeeper  
 ┃ ┣ 📂shopkeeper-gateway  
 ┃ ┣ 📂shopkeeper-notification  
 ┃ ┣ 📂stock  
 ┃ ┣ 📜Dockerfile  
 ┃ ┣ 📜package.bat  
 ┃ ┣ 📜package.sh  
 ┃ ┗ 📜start.sh  
 ┣ 📂supplier  
 ┃ ┣ 📂catalog  
 ┃ ┣ 📂order  
 ┃ ┣ 📂supplier-gateway  
 ┃ ┣ 📂supplier-notification  
 ┃ ┣ 📜Dockerfile  
 ┃ ┣ 📜package.bat  
 ┃ ┣ 📜package.sh  
 ┃ ┗ 📜start.sh  

## Pré-requisitos
- [Java](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
- [Docker](https://docs.docker.com/)
- [Maven](https://maven.apache.org/download.cgi)

## Passo a passo
- Criar os artefatos: executar o arquivo package(bat ou sh) do lojista e do atacadista
- Criar a imagem docker do atacadista: acesse "abe-final > supplier" e execute <docker build -t "supplier:abe-final" .>
- Criar o container do atacadista: acesse "abe-final > supplier" e execute <winpty docker run -d -p 8080:8080 --name supplier {IMAGE_ID}>
- Criar a imagem docker do lojista: acesse "abe-final > shopkeeper" e execute <docker build -t "shopkeeper:abe-final" .>
- Cirar o container do lojista: acesse "abe-final > shopkeeper" e execute <winpty docker run -d -p 9090:9090 --name shopkeeper {IMAGE_ID}>

## APIs
A documentação das APIs foi produzida utilizando o swagger e pode ser acessada através da url http://{endereco:porta}/swagger-ui.html
![Swagger](https://docs.google.com/drawings/d/e/2PACX-1vSjPEdpH_itR_nwox2rU0PJ7uTkopu8wLlPihYjShB4Hz-ZuwHZ8JIK6vzF-t1SN75531ia_VVcgtBd/pub?w=927&h=495)

## Disponibilidade
O [SPRING ACTUATOR](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html) foi utilizado para possibilitar o monitoramento das APIs. Basta executar o grupo de testes a seguir no postman para verificar se as APIs estão disponíveis: [obter testes](https://www.getpostman.com/collections/b80b24f1576c703e9680).
