From : Prashant Mavinkurve
This file contains two items:
-.  Eclipse setup.
-.  Functionality enhancement
-.  Notes on production readiness.

Eclipse based Development:
------------------------
The development was done within eclipse.  
A few things had to be done on eclipse to acheive that:
1. Required Libraries:  The attache snapshot: java_challenge_spring_eclipse_libraries.jpg
   shows all the required libraries.
2. Install lombok
3. install STS:  http://www.codejava.net/ides/eclipse/install-spring-tool-suite-for-existing-eclipse-ide
4. With this I was able to execute the project within eclipse itself.




Functionality enhancement
------------------------
Functionality to transfer the amount from one account to another was developed.
Two tests were added, that covered 
- the positive test.
- negative test for negative amounts or amounts that exceeeded the balance of the source account.

For Production Readiness:
-------------------------
If there was more time around the project, some of the things that 
could be focussed on:
-. Messages framework, so that the enduser can get more meaningful messages.-. 
   Additional Logging throughout the application. This speds up troube shooting espescially in production.
-. If deployed as a microservice, would think of multi-tenancy handling and controlling log levels via external 
   configuration
-. Performance and Load Tests for simulating high-volume/concurrency transactions
-. Accomodate, copyright inclusion at the code level.
-. Including serialVersionUID with the java classes.
-. Use Constants and enumerations for most of the literals in the code.
-. Add Commenting in the right places.
-. Exception Hanndling (Code hardening)
-  Look for ways for having the code build along CI-CD (Continuous Integration and Delivery) guidelines, test automation etc..
- Make the notification service a pub-sub mechanism, so that the calls can be handled as non-blocking queued messages.
