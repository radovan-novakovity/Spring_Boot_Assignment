# Spring_Boot_Assignment

Spring Boot application made by my team, for a class assignment.

We were tasked with creating an application, using Spring Boot, JPA, H2 database, and Thymeleaf template engine. We used the IntelliJ IDEA, which has Apache TomCat webserver integration. Once started, It runs on 8080 port, http://localhost:8080/

There is an Admin user, and a regular User. (Admin/Admin) (TesztElek/Password)

The H2 database credentials. http://localhost:8080/db UserName: sa Password: Password

The autchentication is not handled by Spring Security, but a custom solution. We know that for example It's prone to SQL injection this way, but it was due to time constraints.

It has a Login / Register page, which includes validations for the Username and Password fields, for both Login / Register.

The Admin user has a special page, where it displays every user, and their property values from the H2 database 'USERS' table.

The voting page is, where the user has to pick an ice cream flavour, and once votes, brings them to the results page. On here it displays the number of votes each flavor has recieved so far. The way it works, if a user has their VOTE property set to TRUE, then after redirecting to the results page, it doesn't count their vote again. The application also uses Htpp sessions to implement that, and also the logout feature.
