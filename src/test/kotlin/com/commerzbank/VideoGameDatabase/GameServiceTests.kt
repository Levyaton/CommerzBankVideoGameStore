package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.Helper.Companion.whenever
import com.commerzbank.VideoGameDatabase.controller.GameController
import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.transaction.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExtendWith(SpringExtension::class)
@WebMvcTest(GameService::class)
@RunWith(SpringRunner::class)
open class GameServiceTests {

    @Autowired
    private lateinit var gameService: GameService


    private val sampleDto = GameDto(1,"testName","testPublisher",1,0.3)
    //private val sampleDto2 = GameDto(2,"testName2","testPublisher2",2,0.6)

    private val sampleDtoList = mutableListOf(sampleDto)//,sampleDto2)

    @MockBean
    private lateinit var gameDao: GameDao

    @Test
    open fun dropAll_WhenAny_ThenEmptiesDatabase() {
        var gameNumber = 10

        whenever(gameDao.deleteAll()).then{
            while(gameNumber!=0)
                gameNumber--
        }
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        val result = gameService.dropAll()
        assertEquals(0,gameNumber)
        assertNotNull(result)
    }

    @Test
    open fun get_WhenKnownId_ThenReturnsSampleDto() {
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        assertNotNull(gameService.get(1))
    }

    @Test
    open fun get_WhenUnknownId_ThenReturnsNull() {
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        assertNull(gameService.get(1))
    }

    @Test
    open fun listAll_WhenAny_ThenReturnsListOfSampleDto() {
        val list = listOf(sampleDto.toEntity(),sampleDto.toEntity(), sampleDto.toEntity())
        whenever(gameDao.findAll()).thenReturn(list)
        assertEquals(3,gameService.listAll().size)
    }

    @Test
    open fun paginate_WhenExistingPageAndCount_ThenReturnsCorrectListOfSampleDto() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 2, count = 2)
        assertEquals(2,sublist.size)
        assertEquals("testName3", sublist[0].name)
        assertEquals("testName4", sublist[1].name)
    }

    @Test
    open fun paginate_WhenNonexistingPageAndCount_ThenReturnsEmptyList() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 200, count = 200)
        assertEquals(0,sublist.size)
    }

    @Test
    open fun paginate_WhenNonexistingPageAndExistingCount_ThenReturnsEmptyList() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 200, count = 3)
        assertEquals(0,sublist.size)
    }

    @Test
    open fun paginate_WhenPageIs1AndNonexistingCount_ThenReturnsListofRemainingSampleDtos() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 1, count = 200)
        assertEquals(6,sublist.size)
    }

    @Test
    open fun paginate_WhenExistingNon1PageAndNonExistingCount_ThenReturnsEmptyList() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 2, count = 200)
        assertEquals(0,sublist.size)
    }

    @Test
    open fun paginate_WhenPageAndCountExistButTheirCombinationGoesOverrange_ThenReturnsListofRemainingSampleDtos() {
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
        whenever(gameDao.findAll()).thenReturn(list)

        val sublist = gameService.paginate(page = 2, count = 4)
        assertEquals(2,sublist.size)
    }

    @Test
    open fun create_WhenAny_ThenAddItemToDatabase() {
        val games = mutableListOf<GameDto>()
        whenever(gameDao.save(any<Game>())).then {
           games.add(sampleDto)
        }
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        val result = gameService.create(sampleDto)
        assertEquals(1,games.size)
        assertNotNull(result)
    }

    @Test
    open fun update_WhenIdExists_ThenReturnsSampleDto() {
        val games = mutableListOf(sampleDto)
        val newName  = "test"
        whenever(gameDao.save(any())).then {
            games.removeAt(0)
            val a = sampleDto.copy()
            a.name = newName
            games.add(a)
        }
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(true)
        val result = gameService.update(sampleDto)
        assertEquals(newName,games[0].name)
        assertNotNull(result)
    }

    @Test
    open fun update_WhenIdDoesNotExist_ThenReturnsNull() {
        val games = mutableListOf(sampleDto)
        val newName  = "test"
        whenever(gameDao.save(any())).then {
            games.removeAt(0)
            val a = sampleDto.copy()
            a.name = newName
            games.add(a)
        }
        whenever(gameDao.getOne(anyInt())).thenReturn(sampleDto.toEntity())
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        val result = gameService.update(sampleDto)
        assertNull(result)
    }


    @Test
    open fun delete_WhenIdExists_ThenReturnsSampleDto() {
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
    open fun delete_WhenIdDoesNotExist_ThenReturnsNull() {
        val games = mutableListOf(sampleDto,sampleDto,sampleDto)
        whenever(gameDao.deleteById(anyInt())).then {
            games.removeAt(0)
        }
        whenever(gameDao.existsById(anyInt())).thenReturn(false)
        val result = gameService.delete(1)
        assertEquals(3,games.size)
        assertNull(result)
    }


}