# CommerzBankVideoGameStore

Rest API Commands:


**Get Game**
----
  Returns json data for a game in the database.

* **URL**

  /game/game

* **Method:**

  `GET`
  
*  **URL Params**

   **Required**
   
  `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    {"id":1,"name":"testName1","publisher":"testPublisher1","rating":0,"price":0.0}
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/game/game?id=1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```


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
    [{"id":1,"name":"testName1","publisher":"testPublisher1","rating":0,"price":0.0}, 
    {"id":2,"name":"testName2","publisher":"testPublisher2","rating":5,"price":3.2}]`
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/game/games",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

**Create Game**
----
  Creates a new entry in the database.

* **URL**

  /game/create

* **Method:**

  `POST`
  
*  **URL Params**

  None

* **Data Params**

   **Required**
   
  `dto=[json]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
   None
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/game/create",
      data : "{"name":"sampleName", "publisher":"samplePublisher", "rating" : 0, "price":6.3}"
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
