To start the application open command prompt inside the project directory then run the command

``
./gradlew bootRun
``

git clone  https://github.com/Georgedev2/schedule-transfer-api.git
<br/> 

cd schedule-transfer-api

``
gradle clean install
``

### 1. POST /scheduleTransfer
#### Request:
``
POST /scheduleTransfer
Content-Type: application/json
``


#### Response
``
{
"senderAccountId": "account123",
"recipientAccountId": "account456",
"transferAmount": 1000,
"transferDate": 1738420274924
}
``

* ``senderAccountId:`` The ID of the account sending the money.
* ``recipientAccountId:`` The ID of the account receiving the money.
* ``transferAmount:`` The amount to transfer (should be a positive number).
* ``transferDate:`` The date in milliseconds when the transfer should take place (must be in the future).

#### Response (Success - 201 Created):
``
{
"senderAccountId": "account123",
"recipientAccountId": "account456",
"amount": 1000,
"transferDate": 1738420274924
}
``
* The transfer is successfully scheduled with the provided details.
 #### Response (Error - 400 Bad Request):
If the ``transferAmount`` is negative or ``transferDate`` is in the past:

``
{
"error": "The scheduled amount must not be negative, and also date provided must be in the future",
"hasError": true
}
``
* The request fails due to invalid input (e.g., negative amount or past date).
### 2. GET /scheduleTransfer/{accountId}
This endpoint allows users to fetch all scheduled transfers by  ``accountId``. 

#### Request:
``
GET /scheduleTransfer/account123
`` 

#### Response
``
[
{
"senderAccountId": "account123",
"recipientAccountId": "account456",
"amount": 1000,
"transferDate": 1738420274924
},

{
"senderAccountId": "account123",
"recipientAccountId": "account789",
"amount": 500,
"transferDate": 1738420274924
}

]
``
* Returns a list of all scheduled transfers for account123.

## 🧠 Approach
#### 1. Controller:
  * The controller handles HTTP requests and delegates the business logic to the service layer.
  * It performs basic validation for the input fields (e.g., ensures the transfer amount is positive and the transfer date is in the future).
  * If validation fails, it returns an ErrorResponse with the error message and a 400 Bad Request status.
#### 2. Service Layer:
 *  The service layer processes the core logic, such as saving scheduled transfers into the database and ensuring that transactions are valid.
  * It communicates with the persistence layer (repository) to store and retrieve scheduled transaction records.
#### 3. Utility Class:
   The ScheduledTransactionUtils class contains helper methods that handle validation, such as checking if the transfer date is in the future and if the amount is valid (positive number).
#### 4. Persistence Layer:
   The ScheduledTransactionRepository interacts with the database to persist scheduled transactions and fetch them when needed.
#### 5. Error Handling:
   The controller catches validation errors and returns an ErrorResponse as a JSON object with a 400 Bad Request HTTP status.

#### Example error response

``
{
"error": "The scheduled amount must not be negative, and also date provided must be in the future",
"hasError": true
}
``

### 6. Testing:
* The application include integration tests to verify that the API works as expected.
  *  Integration tests use MockMvc to simulate HTTP requests and validate responses, including error cases.


