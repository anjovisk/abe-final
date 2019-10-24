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
