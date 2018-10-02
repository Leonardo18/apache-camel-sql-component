# Apache-Camel-Sql-Component
Java microservice using ***Apache Camel*** with ***Apache Camel SQL Component*** to insert and select from SQL Server

### Usage
* First thing to do is use the command `docker-compose -f docker-compose-local.yml up` in root folder, this command will make ***SQL Server*** available
* After run tests to have sure everything is available, if tests pass, maybe you need connect to instance of SQL Server with data in docker-compose-local.yml and run the ***data.sql*** to create table where data will be inserted, after this run application and do some post and get in API's

### Acknowledgments
* [RabbitMQ documentation](https://www.rabbitmq.com/api-guide.html)
* [Apache Camel Java DSL](http://camel.apache.org/java-dsl.html)
* [Apache Camel SQL Component](http://camel.apache.org/sql-component.html)



