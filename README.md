The application presented here is a more extensive example of the previous application, 
where I decided to extend the number of base objects and modify the logic presented in the service module.

Application characteristics
- multi-module application: persistence, service, ui, written in java
- ui module is based on the spark framework for sending http requests.
- the service module has been tested using JUnit 5
- application in service modules contains logic aimed at creating various variants of lists of car type objects and its properties.
- data for the application are downloaded from the json file using the Gson library.
- in addition to http requests, the application can also be operated with a simple user menu geared towards 
interacting with the console.
- the application has been made available on github, as well as on dockerhub at: