# CommerzBankVideoGameStore

Rest API Commands:


**Get Game**
----
  Returns json data for a game in the database.

* **URL**

  /api/v1/game/:id

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
      url: "/api/v1/game/1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

**Get Game Count**
----
  Returns an integer, representing the number of elements in the game database.

* **URL**

  /api/v1/games/count

* **Method:**

  `GET`
  
*  **URL Params**

   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    1
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/games/count",
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

  /api/v1/games

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
      url: "/api/v1/games",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
  
  **Paginate Games**
----
  Splits the list of json objects from database into pages with n elements and returns the selected page as a json array

* **URL**

  /api/v1/games?page=:page&count=:count

* **Method:**

  `GET`
  
*  **URL Params**

   `page=[integer]` - Which page do you want?
   `count=[integer]` - How many elements are there per page?

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
      url: "/api/v1/games?page=1&count=2",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
 **Find games by name**
----
  Returns json data about each game in the database with a given name.

* **URL**

  /api/v1/games?name=:name

* **Method:**

  `GET`
  
*  **URL Params**

    **Required**
   
  `name=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    [{"id":1,"name":"sample","publisher":"testPublisher1","rating":0,"price":0.0}, 
    {"id":2,"name":"sample","publisher":"testPublisher2","rating":5,"price":3.2}]`
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/games?name=sample",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
   **Find games by Publisher**
----
  Returns json data about each game in the database with a given publisher.

* **URL**

  /api/v1/games?publisher=:publisher

* **Method:**

  `GET`
  
*  **URL Params**

    **Required**
   
  `publisher=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    [{"id":1,"name":"sample1","publisher":"testPublisher1","rating":0,"price":0.0}, 
    {"id":2,"name":"sample2","publisher":"testPublisher1","rating":5,"price":3.2}]`
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/games?publisher=testPublisher1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
  
  **Find games by rating**
----
  Returns json data about each game in the database with a given rating.

* **URL**

  /api/v1/games?rating=:rating

* **Method:**

  `GET`
  
*  **URL Params**

    **Required**
   
  `rating=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    [{"id":1,"name":"sample1","publisher":"testPublisher1","rating":5,"price":0.0}, 
    {"id":2,"name":"sample2","publisher":"testPublisher2","rating":5,"price":3.2}]`
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/games?rating=5",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
 **Find games by price**
----
  Returns json data about each game in the database with a given price.

* **URL**

  /api/v1/games?price=:price

* **Method:**

  `GET`
  
*  **URL Params**

    **Required**
   
  `price=[float]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    [{"id":1,"name":"sample1","publisher":"testPublisher1","rating":0,"price":3.2}, 
    {"id":2,"name":"sample2","publisher":"testPublisher2","rating":5,"price":3.2}]`
 
* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
 
 * **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/games?price=3.2",
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

  /api/v1/game

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
      url: "/api/v1/game",
      data : "{"name":"sampleName", "publisher":"samplePublisher", "rating" : 0, "price":6.3}",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
 
 **Create Games**
----
  Creates multiple new entries in the database.

* **URL**

  /api/v1/games

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
      url: "/api/v1/games",
      data : "[{"name":"sampleName", "publisher":"samplePublisher", "rating" : 0, "price":6.3},{"name":"sampleName2", "publisher":"samplePublisher", "rating" : 0, "price":6.3}]"
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
  
**Update Game**
----
  Updates an entry in the database.

* **URL**

  /api/v1/game

* **Method:**

  `PUT`
  
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
      url: "/api/v1/game",
      data : "{"name":"sampleName", "publisher":"samplePublisher", "rating" : 0, "price":6.3}"
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
  
**Delete Game**
----
  Deletes an entry in the database.

* **URL**

  /api/v1/game/:id

* **Method:**

  `DELETE`
  
*  **URL Params**

   **Required**
   
  `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
   None
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/game/1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```

**Delete All Games**
----
Drops all entries in the database.

* **URL**

  /api/v1/game/:id

* **Method:**

  `DELETE`

*  **URL Params**

   **Required**

`id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `
    None

* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/api/v1/game/1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```

