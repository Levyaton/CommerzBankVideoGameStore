package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.Helper.Companion.whenever
import com.commerzbank.VideoGameDatabase.controller.GameController
import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import javafx.scene.control.Pagination
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.transaction.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExtendWith(MockitoExtension::class)
class GameServiceTests {


    private lateinit var gameService: GameService


    private val sampleDto = GameDto(1,"testName","testPublisher",1,0.3)
    //private val sampleDto2 = GameDto(2,"testName2","testPublisher2",2,0.6)


    @Mock
    private lateinit var gameDao: GameDao


    @BeforeEach
    fun setup(){
        gameService = GameService(gameDao)
    }

    @Test
    fun dropAll_WhenAny_ThenEmptiesDatabase() {
        var gameNumber = 10

        whenever(gameDao.deleteAll()).then{
            while(gameNumber!=0)
                gameNumber--
        }
        val result = gameService.dropAll()
        assertEquals(0,gameNumber)
        assertNotNull(result)
    }

    @Test
    fun get_WhenKnownId_ThenReturnsSampleDto() {
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        assertNotNull(gameService.get(1))
    }

    @Test
    fun get_WhenUnknownId_ThenReturnsNull() {
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        assertNull(gameService.get(1))
    }

    @Test
    fun listAll_WhenAny_ThenReturnsListOfSampleDto() {
        val list = listOf(sampleDto.toEntity(),sampleDto.toEntity(), sampleDto.toEntity())
        whenever(gameDao.findAll()).thenReturn(list)
        assertEquals(3,gameService.listAll().size)
    }

    @Test
    fun paginate_WhenExistingPageAndCount_ThenReturnsCorrectListOfSampleDto() {
        val entity1 = sampleDto.toEntity()
        entity1.name = sampleDto.name + 1
        val entity2 = sampleDto.toEntity()
        entity2.name = sampleDto.name + 2
        val entity3 = sampleDto.toEntity()
        entity3.name = sampleDto.name + 3
        val entity4 = sampleDto.toEntity()
        entity4.name = sampleDto.name + 4
        val entity5 = sampleDto.toEntity()
        entity5.name = sampleDto.name + 5
        val entity6 = sampleDto.toEntity()
        entity6.name = sampleDto.name + 6

        val list = listOf(entity3,entity4)
        whenever(gameDao.listAll(PageRequest.of(1, 2))).thenReturn(list)

        val sublist = gameService.listAll(page = 1, size = 2)
        assertEquals(2,sublist.size)
        assertEquals("testName3", sublist[0].name)
        assertEquals("testName4", sublist[1].name)
    }

    @Test
    fun paginate_WhenNonexistingPageAndCount_ThenReturnsEmptyList() {
        val entity1 = sampleDto.toEntity()
        entity1.name = sampleDto.name + 1
        val entity2 = sampleDto.toEntity()
        entity2.name = sampleDto.name + 2
        val entity3 = sampleDto.toEntity()
        entity3.name = sampleDto.name + 3
        val entity4 = sampleDto.toEntity()
        entity4.name = sampleDto.name + 4
        val entity5 = sampleDto.toEntity()
        entity5.name = sampleDto.name + 5
        val entity6 = sampleDto.toEntity()
        entity6.name = sampleDto.name + 6

        val list = listOf(entity1,entity2,entity3,entity4,entity5,entity6)

        val sublist = gameService.listAll(page = 200, size = 200)
        assertEquals(0,sublist.size)
    }

    @Test
    fun paginate_WhenNonexistingPageAndExistingCount_ThenReturnsEmptyList() {
        val entity1 = sampleDto.toEntity()
        entity1.name = sampleDto.name + 1
        val entity2 = sampleDto.toEntity()
        entity2.name = sampleDto.name + 2
        val entity3 = sampleDto.toEntity()
        entity3.name = sampleDto.name + 3
        val entity4 = sampleDto.toEntity()
        entity4.name = sampleDto.name + 4
        val entity5 = sampleDto.toEntity()
        entity5.name = sampleDto.name + 5
        val entity6 = sampleDto.toEntity()
        entity6.name = sampleDto.name + 6

        val list = listOf(entity1,entity2,entity3,entity4,entity5,entity6)


        val sublist = gameService.listAll(page = 200, size = 3)
        assertEquals(0,sublist.size)
    }



    @Test
    fun update_WhenIdExists_ThenReturnsSampleDto() {
        val dto = sampleDto
        val games = mutableListOf(sampleDto)
        val newName  = "test"
        dto.name = newName
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        val result = gameService.update(dto.toUpdateGameDto(),1)
        assertNotNull(result)
        assertEquals(newName,result.name)
    }

    @Test
    fun update_WhenIdDoesNotExist_ThenReturnsNull() {
        val games = mutableListOf(sampleDto)
        val newName  = "test"
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        val result = gameService.update(sampleDto.toUpdateGameDto(),1)
        assertNull(result)
    }


    @Test
    fun delete_WhenIdExists_ThenReturnsSampleDto() {
        val games = mutableListOf(sampleDto,sampleDto,sampleDto)
        whenever(gameDao.deleteById(anyInt())).then {
            games.removeAt(0)
        }
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        val result = gameService.delete(1)
        assertEquals(2,games.size)
        assertNotNull(result)
    }

    @Test
    fun delete_WhenIdDoesNotExist_ThenReturnsNull() {
        val games = mutableListOf(sampleDto,sampleDto,sampleDto)
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        val result = gameService.delete(1)
        assertEquals(3,games.size)
        assertNull(result)
    }

    @Test
    fun findByName_WhenNameExists_ThenReturnsListOfMatchingNamesDtos() {
        whenever(gameDao.findByName(anyString())).thenReturn(listOf(sampleDto.toEntity(),sampleDto.toEntity(),sampleDto.toEntity()))
        val sublist = gameService.findByName("testName")
        assertEquals(3,sublist.size)
    }

    @Test
    fun findByName_WhenNameDoesNotExists_ThenReturnsEmptyList() {
        whenever(gameDao.findByName(anyString())).thenReturn(listOf())
        val sublist = gameService.findByName("testName")
        assertEquals(0,sublist.size)
    }

    @Test
    fun findByPublisher_WhenPublisherExists_ThenReturnsListOfMatchingPublisherDtos() {
        whenever(gameDao.findByPublisher(anyString())).thenReturn(listOf(sampleDto.toEntity(),sampleDto.toEntity(),sampleDto.toEntity()))
        val sublist = gameService.findByPublisher("testName")
        assertEquals(3,sublist.size)
    }

    @Test
    fun findByPublisher_WhenPublisherDoesNotExists_ThenReturnsEmptyList() {
        whenever(gameDao.findByPublisher(anyString())).thenReturn(listOf())
        val sublist = gameService.findByPublisher("testName")
        assertEquals(0,sublist.size)
    }

    @Test
    fun findByPrice_WhenPriceExists_ThenReturnsListOfMatchingPriceDtos() {
        whenever(gameDao.findByPrice(anyDouble())).thenReturn(listOf(sampleDto.toEntity(),sampleDto.toEntity(),sampleDto.toEntity()))
        val sublist = gameService.findByPrice(0.1)
        assertEquals(3,sublist.size)
    }

    @Test
    fun findByPrice_WhenPriceDoesNotExists_ThenReturnsEmptyList() {
        whenever(gameDao.findByPrice(anyDouble())).thenReturn(listOf())
        val sublist = gameService.findByPrice(0.1)
        assertEquals(0,sublist.size)
    }

    @Test
    fun findByRating_WhenRatingExists_ThenReturnsListOfMatchingRatingDtos() {
        whenever(gameDao.findByRating(anyInt())).thenReturn(listOf(sampleDto.toEntity(),sampleDto.toEntity(),sampleDto.toEntity()))
        val sublist = gameService.findByRating(1)
        assertEquals(3,sublist.size)
    }

    @Test
    fun findByRating_WhenRatingDoesNotExists_ThenReturnsEmptyList() {
        whenever(gameDao.findByRating(anyInt())).thenReturn(listOf())
        val sublist = gameService.findByRating(1)
        assertEquals(0,sublist.size)
    }

}