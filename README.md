# CommerzBankVideoGameStore

Rest API Commands:


**List Games**
----
  Returns json data about each game in the database.

* **URL**

  /game/games

* **Method:**

  `GET`
  
*  **URL Params**

   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    [{"id":1,"name":"testName","publisher":"testPublisher","rating":0,"price":0.0}, 
    {"id":2,"name":"testName","publisher":"testPublisher","rating":0,"price":0.0}]`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    
