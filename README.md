# Payments System


The project is intended to make payments with accounts. There are two roles: user and admin.  
You can as user:
- Create accounts
- View account
- Refill an account from another one
- Make payment
- Locking account
- View payments
- Filter payments  

You can as admin:
- View all accounts
- View all payments
- Edit accounts
- Filter account/payments
- Unlocking accounts
- Set status DELETED for accounts

### Tech

You should have such programms for starting project:

1. Apache Tomcat Version 8.5.4
2. Apache Maven Version 3.3.9
3. JDK 8
4. MySQL Server 5.5

Make next steps before deploy:
- Register environment variables:
    - for Tomcat - CATALINA_HOME, PATH
    - for Maven - M2_HOME, PATH
    - for JDK - JAVA_HOME, PATH
    - for MySQL - PATH
- Change settings of server Tomcat inside settings.xml in package installed Maven ../conf for the following:
```sh
<server>
<id>Tomcat</id>
<username>tomcat</username>
<password>tomcat</password>
</server>
```
- Add user with following username, password, roles inside tomcat-users.xml in package installed Tomcat ../conf:
```sh
<user username="tomcat" password="tomcat"
roles="tomcat,manager-gui,manager-script"/>
```
- Open the settings of access to the data base for project   
(path ..\dao\src\main\resources\hibernate.properties)  
and check the port, username, password (change they if it need).
- Open the file mysql.bat (path ..\paymentsSystem\bin) and change login, password "root" (if they are another) for access to the MySQL data base in first, second, last lines.
- Start the server Apache Tomcat in package installed Tomcat ../bin/startup.bat

### Installation
You need run three steps for deploying project:  
1. Start mysql.bat (path ..\paymentsSystem\bin)  
2. Start systemPayments.bat (path ..\paymentsSystem\bin)  
3. Open Web Browser. Input there http://localhost:8080/paymentsSystem/(check port of the server Tomcat, here 8080)

*************************************************************************
Input data for log in as user:  
*login* : **user**  
*password*: **user**
