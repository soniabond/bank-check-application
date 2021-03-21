# Bank java spring application

This application allows user to track his income and expend from Monobank and PrivatBank in one place. It provides
a user following functionality:
- connecting Privatbank and Monobank accounts to profile;
- checking total balance, discharges and income on all connected cards of user to this application;
- tracking discharges and income amounts on all provided categories by application;
- detalized analysis of each and every category activity;
- adding and changing spending limits on every category;
- graph visualization of relation of monthly income and expend; of expend on each category;
- sing in and sign up,

This application connects to Privatbank and Monobank through their API and analyses data received. It also parses 
all the discharges got from bank account of registered user and spits them into different categories for user's 
comfort. The base of category-pattern relations in fulfilled be ADMIN.

Functionality, provided for admin:
- add new admins;
- fulfill base of category-pattern relations.

Technologies: Java 11, Spring Data JPA, Spring Security, Spring ORM, MySQL, Docker, JUnit, Mockito.

Check avaliable endpoint http://localhost:8080/swagger-ui/index.html

![Alt text](https://github.com/soniabond/bank-check-application/blob/master/Screenshot%20from%202021-03-21%2021-10-28.png)


