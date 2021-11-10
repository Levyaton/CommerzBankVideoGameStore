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

**Get Game Count**
----
  Returns an integer, representing the number of elements in the game database.

* **URL**

  /game/count

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
      url: "/game/count",
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
  
  **Paginate Games**
----
  Splits the list of json objects from database into pages with n elements and returns the selected page as a json array

* **URL**

  /game/paginate

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
      url: "/game/paginate?page=1&count=2",
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

  /game/gamesWithName

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
      url: "/game/gamesWithName?name=sample",
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

  /game/gamesWithPublisher

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
      url: "/game/gamesWithPublisher?publisher=testPublisher1",
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

  /game/gamesWithRating

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
      url: "/game/gamesWithRating?rating=5",
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

  /game/gamesWithPrice

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
      url: "/game/gamesWithPrice?price=3.2",
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
 
 **Create Games**
----
  Creates multiple new entries in the database.

* **URL**

  /game/createMultiple

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
      url: "/game/createMultiple",
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

  /game/update

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
      url: "/game/update",
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
  Creates a new entry in the database.

* **URL**

  /game/delete

* **Method:**

  `POST`
  
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
      url: "/game/delete?id=1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });
  ```
   

