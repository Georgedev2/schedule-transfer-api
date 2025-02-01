To start the application open command prompt inside the project directory then run the command

``
./gradlew bootRun
``

git clone git remote add origin https://github.com/Georgedev2/schedule-transfer-api.git
cd schedule-transfer-api

mvn clean install


Approach
The application uses the MVC  design pattern, where we divide the entire application components into three parts namely: presentations, business logic and persistence layer. The presentation in this case is a JSON send in response to the request. The MVC pattern used in this project ensures that each part of a program is responsible for only one job.

Example API requests & Response

Requests


Response