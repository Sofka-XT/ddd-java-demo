# Demo Java and Domain Driven Design (DDD)

The purpose of this demo is to provide a set of abstraction and implementations over different parents to make the implementation 
of DDD(Domain Driven Design).

The main purpose is to provide such an example of abstraction in a mostly generic way, trying to adapt for an specific aim to be easy 
to change, personalize and extend.  

## 1. Create a Multi-Module Project with Maven

### First: we create a project with the nex command, you should change DgroupId and DartifactId for the data you want

mvn archetype:generate -DgroupId=co.com.sofka -DartifactId=ddd-java-demo 

Once the parent is generated, we've to open the pom.xml file located in the parent's directory and change the packaging to pom.

> < packaging > pom </ packaging >

here we're declaring that project will serve as a parent or an aggregator – it won't produce further artifacts.

### Second: we create the submodules

Enter in the parent directory

> cd ddd-java-demo

Then run generate commands:

mvn archetype:generate -DgroupId=co.com.sofka  -DartifactId=core 

mvn archetype:generate -DgroupId=co.com.sofka  -DartifactId=spring

### Third: building the project 

> mvn package

## 2. Get Started

### The dependencies needed for this project

> 1. Generic dependency for ddd Java - https://mvnrepository.com/artifact/co.com.sofka/domain-driven-design
```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>domain-driven-design</artifactId>
       <version>0.1.2</version>
     </dependency>
```
> 2. SLF4J API dependency for logging facade - https://mvnrepository.com/artifact/org.slf4j/slf4j-api
```
    <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-api</artifactId>
       <version>1.7.30</version>
    </dependency>
```
> 3. Junit dependency for unit test - https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
```
     <dependency>
       <groupId>org.junit.jupiter</groupId>
       <artifactId>junit-jupiter</artifactId>
       <version>5.5.1</version>
       <scope>test</scope>
     </dependency>
 ```
> 4. Jackson Core for abstraction implementation for JSON - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
```
     <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.9.4</version>
     </dependency>
 ```
> 5. Firebase for Admin manage with The Firebase Platform - https://mvnrepository.com/artifact/com.google.firebase/firebase-admin
```
     <dependency>
       <groupId>com.google.firebase</groupId>
       <artifactId>firebase-admin</artifactId>
       <version>6.8.1</version>
     </dependency>
 ```
> 6. Springframework Starter Web for building web, RESTFUL - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
```
     <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
       <version>2.2.4.RELEASE</version>
       <scope>compile</scope>
       <exclusions>
         <exclusion>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-logging</artifactId>
         </exclusion>
       </exclusions>
     </dependency>
 ```
> 7. Gson for convert objects(POJO) to JSON - https://mvnrepository.com/artifact/com.google.code.gson/gson
```
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.2</version>
    </dependency>
 ```
> 8. Springframework Starter AMQP for using AMQP and RabbitMQ - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-amqp
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-amqp</artifactId>
      <version>2.2.4.RELEASE</version>
    </dependency>
 ```
> 9. Spring Data MongoDB for manage with Mongo - https://mvnrepository.com/artifact/org.springframework.data/spring-data-mongodb
```
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-mongodb</artifactId>
      <version>2.2.4.RELEASE</version>
    </dependency>
 ```


### Platforms that is necessary to use

### Firebase: 

We need to generate the database in firebase, download the credentials file and save it in the project folder with the name event-sourcing-demo-firebase.json

###We use docker for generate RabbitMQ and MongoDB as containers and use it in the project

#### Docker RabbitMQ
> docker run -d -p 15672:15672 -p 5672:5672 --name rabbit-ddd rabbitmq:3-management

you must generate the exchange: 'ddd-demo-java' which we use as an event lake.

#### Docker MongoBD
> docker run -p 27017:27017 --name some-mongo -d mongo:3

### Patters
> 1. Command Query Responsibility Segregation - CQRS - https://docs.microsoft.com/es-es/azure/architecture/patterns/cqrs
> 2. Event Sourcing - https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing
> 3. Publisher and subscriber - https://docs.microsoft.com/en-us/azure/architecture/patterns/publisher-subscriber
> 4. Singleton - https://refactoring.guru/design-patterns/singleton


References: 
> https://www.baeldung.com/maven-multi-module





