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
    [{"id":1,"name":"testName1","publisher":"testPublisher1","rating":0,"price":0.0}, 
    {"id":2,"name":"testName2","publisher":"testPublisher2","rating":5,"price":3.2}]`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

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
      url: "/users/1",
      data : "{"name":"testName", "rating" : 0, "price":6.3}"
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
