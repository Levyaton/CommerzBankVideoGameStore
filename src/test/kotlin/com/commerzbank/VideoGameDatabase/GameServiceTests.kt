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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockitoExtension::class)
@WebMvcTest(GameService::class)
@RunWith(SpringRunner::class)
open class GameServiceTests {

    @Autowired
    private lateinit var gameService: GameService

    private val sampleDto = GameDto(1,"testName1","testPublisher1",1,0.3)
    //private val sampleDto2 = GameDto(2,"testName2","testPublisher2",2,0.6)

    private val sampleDtoList = mutableListOf(sampleDto)//,sampleDto2)

    @Mock
    private lateinit var gameDao: GameDao

    @Test
    open fun delete_WhenValidInput_ThenReturnsSampleDto() {
        whenever(gameDao.count()).thenReturn(5)

    }


}