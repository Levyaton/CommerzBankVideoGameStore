package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.service.GameService
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.assertThrows

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.HttpMethod

import org.springframework.http.ResponseEntity

import org.springframework.http.HttpEntity
import org.springframework.test.annotation.DirtiesContext
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import kotlin.test.assertFails
import kotlin.test.assertFailsWith


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
open class GameControllerTests {

    val testGameName = "testName"
    val testGamePublisher = "testPublisher"
    val testGameRating = 5
    val testGamePrice = 0.1


    @LocalServerPort
    var port = 8080

    private val gson = Gson()
    private var restTemplate = TestRestTemplate()

    private var headers = HttpHeaders()

    @Autowired
    lateinit var gameService: GameService



    private fun createSampleData(count:Int, dto: GameDto){

    }

    private fun createURLWithPort(uri: String): String {
        return "http://localhost:$port/api/v1$uri"
    }

    @Transactional
    open fun prepareDB(gameNumber:Int, name: String, publisher: String, rating: Int, price:Double){
        //gameService.dropAll()
        Log.log.info(gameService.listAll().size.toString())
        val dto = GameDto(id = null, name = name, publisher = publisher, rating = rating, price = price)
        for(x in 0 until gameNumber){
            gameService.create(dto)
        }
    }


    private fun makeRequest(url:String,method:HttpMethod): ResponseEntity<String>{
        val entity = HttpEntity<String?>(null, headers)
        return restTemplate.exchange(
            createURLWithPort(url), method, entity, String::class.java
        )
    }

   @Test
    @Throws(Exception::class)
    fun testListAll() {
        val gameNumber = 4
        prepareDB(gameNumber,testGameName,testGamePublisher,testGameRating,testGamePrice)
        val response = makeRequest("/games",HttpMethod.GET)
        val games = gson.fromJson(response.body, Array<GameDto>::class.java)
        assertTrue(games.size==gameNumber)
    }

    @Test
    @Throws(Exception::class)
    fun testPaginate(){
        val gameNumber = 50
        val pageNumber = 3
        val itemsPerPage = 3
        prepareDB(gameNumber,testGameName,testGamePublisher,testGameRating,testGamePrice)
        val response = makeRequest("/games?page=$pageNumber&count=$itemsPerPage",HttpMethod.GET)
        val games = gson.fromJson(response.body, Array<GameDto>::class.java)
        for(x in 0 until itemsPerPage){
            val expectedId = (pageNumber-1)*itemsPerPage+x+1
            val current = games[x]
            assertEquals(expectedId,current.id)
        }
    }


    @Test
    @Throws(Exception::class)
    fun testGet(){
        val gameNumber = 5
        for(x in 1 until gameNumber){
            prepareDB(gameNumber = 1,testGameName+x.toString(),testGamePublisher+x.toString(),testGameRating+x,testGamePrice+x)
        }
        val response = makeRequest("/game/1",HttpMethod.GET)
        val game = gson.fromJson(response.body, GameDto::class.java)
        assertEquals(testGameName+"1", game.name)
        assertEquals(testGamePublisher+"1", game.publisher)
        assertEquals(testGamePrice+1, game.price)
        assertEquals(testGameRating+1, game.rating)
        assertEquals(1,game.id)
    }

    @Test
    @Throws(Exception::class)
    fun testDelete(){
        val gameNumber = 5
        for(x in 1 until gameNumber){
            prepareDB(gameNumber = 1,testGameName+x.toString(),testGamePublisher+x.toString(),testGameRating+x,testGamePrice+x)
        }
        assertEquals(gameNumber-1,gameService.listAll().size)
        val response = makeRequest("/game/1",HttpMethod.DELETE)
        assertEquals(gameNumber-2,gameService.listAll().size)
        assertFailsWith<EntityNotFoundException>{
            gameService.get(0)
        }

    }
}