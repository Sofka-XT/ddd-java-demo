# Demo Java and Domain Driven Design (DDD)

## 1. Create a Multi-Module Project with Maven

### First: we create a project with the nex command, ypu should change DgroupId and DartifactId for the data you want

mvn archetype:generate -DgroupId=co.com.sofka -DartifactId=ddd-java-demo 

Once the parent is generated, we have to open the pom.xml file located in the parent's directory and change the packaging to pom.

> < packaging > pom </ packaging >

here  we're declaring that project will serve as a parent or an aggregator – it won't produce further artifacts.

### second: we create the submodules

Enter in the parent directory

> cd ddd-java-demo

Then run generate commands:

mvn archetype:generate -DgroupId=co.com.sofka  -DartifactId=core 

mvn archetype:generate -DgroupId=co.com.sofka  -DartifactId=generic 

mvn archetype:generate -DgroupId=co.com.sofka  -DartifactId=spring

### third: building the project 

> mvn package


References: 
> https://www.baeldung.com/maven-multi-module





