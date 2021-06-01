# Rest API Product order Application
Product order application is Rest API which allows to:
- add new products,
- get list of all products,
- place an order which can contain more than one product,
- update product,
- recalculate order,
- get list of orders which were placed in given period of time

Database layer is assured by h2 database:  
`h2.console.path=/h2`

Before run the application, create .jar file by using the command `mvn package` and then 
run application in command line `java -jar product-order-2.4.5.jar`.

Api documentation is available in link below:</br>
https://documenter.getpostman.com/view/12958143/TzXtJfgs
