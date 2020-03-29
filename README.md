# How to run the app?

## 1. What you need before starting :
- jdk-11.0.1.jdk
- apache-tomcat-9.0.21
- apache-maven-3.6.1
- PostgreSQL 10

## 2. Fork the configuration repository
https://github.com/majidaDev/projet7_config

## 3. Fork or download the repository Github
https://github.com/majidaDev/projet_7

## 4. Prepare the database (with pgAdmin)
- Run the script projet_7_biblio.sql in pgAdmin

## 5. Configure application.properties in this configuration repository (mbook and mperson)

## 6. Generate a package for each service and deploy with tomcat
You have to generate a package for each service we need **(follow the order bellow)** 
1. Open your console
2. Go in the service file  
3. Do a : ***mvn clean install*** AND ***./mvnw spring-boot:run*** **(for each service and follow the order bellow)**
5. Go in your tomcat file (here apache-tomcat-9.0.21) with your console, go in bin/ and do : ***bash startup.sh***

**THE ORDER TO FOLLOW TO DEPLOY THE APPLICATION**

| Ordre  |   Microservice  | server.port |
| :----: | :------------- | :---------: |
|   1    | config-server   |    9101     |
|   2    | eureka-server   |    9102     |
|   3    | zuul-server     |    9004     |
|   4    | mbook           |    9090     |
|   5    | mperson         |    9091     |
|   6    | client-ui       |    9089     |

## Open the application and GO !
127.0.0.1:9089/
