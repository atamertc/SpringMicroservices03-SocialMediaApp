#ilk olarak kullanılacak JDK sürümü seçilir.
FROM amazoncorretto:19
#--------------------------------------------
# Jar dosyasının docker'a kopyalanması:
#1.yol:
#COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

#2.yol: Mikroservislerde her bir jar file'i otomatik bulup yukler
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#--------------------------------------------
# Eğer sistemde güncellemelerin yapılmasını istersen.
CMD apt-get update -y
#--------------------------------------------
# Jarı çalıştırma komutu:
ENTRYPOINT ["java","-jar","/app.jar"]
#--------------------------------------------

#============================= PostgreSQL Container ================================
#docker run --name vm_sma_postgresql -e POSTGRES_PASSWORD=root --net sma_network -d -p 5959:5432 postgres

#============================= ElasticSearch Container ================================
#docker run -d --name vm_sma_elasticsearch --net sma_network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --memory=512m elasticsearch:7.17.3

#============================= RabbitMQ Container ================================
#docker run -d --hostname my-rabbit --name vm_sma_rabbitmq --net sma_network -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=user -p 15672:15672 -p 5672:5672 --memory=128m rabbitmq:3-management

#============================= Redis Container ================================
#docker run --name vm_sma_redis --net sma_network -p 6379:6379 --memory=128m -d redis

#============================= Zipkin Container ================================
#docker run --name vm_sma_zipkin --net sma_network -d -p 9411:9411 --memory=256m openzipkin/zipkin

#///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#============================= git-config-server ================================
#docker build --build-arg JAR_FILE=git-config-server/build/libs/git-config-server-v.0.0.1.jar -t atamertc/sma_configserver:v001 .

#============================= auth-service ================================
#docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t atamertc/sma_authservice:v001 .

#============================= user-service ================================
#docker build --build-arg JAR_FILE=user-service/build/libs/user-service-v.0.0.1.jar -t atamertc/sma_userservice:v001 .

#============================= api-gateway-service ================================
#docker build --build-arg JAR_FILE=api-gateway-service/build/libs/api-gateway-service-v.0.0.1.jar -t atamertc/sma_apigateway:v001 .














