# revotask
  Design and implement a RESTful API (including data model and the backing implementation)
for money transfers between accounts.
Explicit requirements:
  1. You can use Java, Scala or Kotlin.
  2. Keep it simple and to the point (e.g. no need to implement any authentication).
  3. Assume the API is invoked by multiple systems and services on behalf of end users.
  4. You can use frameworks/libraries if you like (except Spring), but don't forget about
      requirement #2 – keep it simple and avoid heavy frameworks.
  5. The datastore should run in-memory for the sake of this test.
  6. The final result should be executable as a standalone program (should not require
      a pre-installed container/server).
  7. Demonstrate with tests that the API works as expected.

#API (NOTE: the server runs on localhost:4567)
  You can find detailed API interaction collection example in "revotask.postman_collection.json" at the root of the project
  Here is a shorter description:
     -name: getAllUsers  , method: GET , url: /users
     -name: createUser   , method: POST, url: /users?userName=:userName&accountName=:accountName&accountAmount=:amount
     -name: updateUser   , method: PUT , url: /users/:userId?newUserName=:userName
     -name: findUserById , method: GET , url: /users/:userId
     -name: deleteUser   , method: PUT , url: /users/delete/:userId
     
     -name: transferMoney, method: PUT , url: /transfer?sourceUserId=:userId&destinationUserId=:userId&amount=:amount
     
#Basic project description
  The application is a standalone executable jar. (NOTE: there are already users and account in the DB imported via import.sql)
  After it is executed a simple UI will appear with two buttons for starting and stoping the server. I decided to implement it
  for the sake of convenience: it is annoying when java process still runs in the background when it is not needed anymore, and
  you have to manually end its task in Task Manager.
  
  The project is based on Spark framework for simple lightweight REST server, H2 inmemory DB, Hibernate for ORM and
      Spock framework for testing.
  
  #Model decription
    There are four main models: User, Account, Ledger and Transfer
    -User has name and Account, is used like Account holder with unidirectional connection (I thought that potentially User will
        be able to have multiple Accounts, that's why I did not combine those two entities);
    -Account has name and balance;
    -Ledger is a history object that reflects Account state throughout the time. Has inforamtion about Account, start and end
        balances and entry time;
    -Transfer is a history object that reflects operations happened between two accounts. Has information about source Account,
        destination Account, operation amount and entry time;
        
    Each entity interacts with DB through Dao layer. All persistance classes are incapsulated in PersistanceContext class for
        convenience.
        
    Sessions and Transactions are managed on stateless Service layer and are also incapsulated in ServiceContext class.
    
    Controller layer is represented with controller classes and route providers.
    Controlelrs consist of a number of Spark HTTP request mappers and coresponding route providers.
    Route providers supply controllers with implementations of Route's finctional interface by exploiting corresponding Services
    
    All responses are represented with JSON. Marshalling and Demarshalling is done by Gson lib.
    



*there is a littele easter egg. Make sure Sound On when pushing the "Start server" button.
