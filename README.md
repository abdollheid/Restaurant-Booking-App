# Restaurant-Booking-App
retaurant booking app built in using spring boot and angularjs. 

- Backend is served using spring boot, hibernate. it serves the main functionaltiy through APIs controllers as well as view controller to handle UI.

- Mysql is used as database solution, below is the design used. 

- Front-end implemented using Angularjs


## Database Design 
3 tables to mange the interactions between the users and the tables.
The following data model explains the tables relations and properties. 
![](presentation-images/ER.PNG?raw=true)

## Website Screens

![](presentation-images/screens-1.png?raw=true)
<br>

![](presentation-images/screens-2.png?raw=true)
<br>

![](presentation-images/screens-3.png?raw=true)
<br>

![](presentation-images/screens-4.png?raw=true)
<br>


## Requirements
1. java 8+.
2. MySql 5.5


## To Run App
1. clone the app and navigate to app main folder.  
2. run `sql-scripts/Restaurant-init.sql` MySql database script to set the necessary database and tables. 
3. run the command `.\mvnw spring-boot:run` for Windows,  `./mvnw spring-boot:run` for Mac/Linux.
4. the app should be running on `http://localhost:8787/`
5. to run tests use the command `.\mvnw test` for Windows,  `./mvnw test` for Mac/Linux.
6. if necessary, server port can be change from app configuration in `src/main/resources/application.properties`.



