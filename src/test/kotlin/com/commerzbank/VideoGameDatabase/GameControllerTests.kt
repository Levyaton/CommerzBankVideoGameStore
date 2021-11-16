package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.controller.GameController
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.httpResponse.ResponseBody
import com.commerzbank.VideoGameDatabase.service.GameService
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate

import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.transaction.Transactional
import kotlin.test.assertFalse
import org.springframework.boot.test.mock.mockito.MockBean

import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.OngoingStubbing
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.*
import org.springframework.http.RequestEntity.get
import org.springframework.http.RequestEntity.post


import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(GameController::class)
@RunWith(SpringRunner::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
open class GameControllerTests {

    val url = "/api/v1"
    val gson = Gson()

    @Autowired
    private lateinit var mockMvc: MockMvc


    @MockBean
    lateinit var gameService: GameService

    private val mockDto = GameDto(1, "testName", "testPublisher", 1, 1.0)


    private fun <T> whenever(methodCall: T): OngoingStubbing<T> =
        Mockito.`when`(methodCall)


    @Test
    open fun create_WhenValidInput_ThenReturns200() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/game")
                .content(gson.toJson(mockDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful)
    }

    @Test
    open fun create_WhenInvalidInput_ThenReturns400() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/game")
                .content("Bad Input")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
    }

    @Test
    open fun get_WhenValidInput_ThenReturns200() {
        whenever(gameService.get(anyInt())).thenReturn(mockDto)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/game/1")).andExpect(jsonPath("name").value("testName"))
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("publisher").value("testPublisher"))
            .andExpect(jsonPath("rating").value(1))
            .andExpect(jsonPath("price").value(1.0))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    open fun get_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.get(anyInt())).thenReturn(mockDto)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/game/mockBadRequest"))
            .andExpect(status().isBadRequest)
    }

    @Test
    open fun get_WhenUnknownId_ThenReturns404() {
        whenever(gameService.get(anyInt())).thenReturn(null)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/game/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    open fun delete_WhenValidInput_ThenReturns200() {
        whenever(gameService.delete(anyInt())).thenReturn(mockDto)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/game/1"))
            .andExpect(status().isOk)
    }

    @Test
    open fun delete_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.delete(anyInt())).thenReturn(mockDto)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/game/mockBadRequest"))
            .andExpect(status().isBadRequest)
    }

    @Test
    open fun delete_WhenUnknownId_ThenReturns404() {
        whenever(gameService.delete(anyInt())).thenReturn(null)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/game/1"))
            .andExpect(status().isNotFound)
    }


    @Test
    open fun count_WhenCalled_ThenReturns200() {
        whenever(gameService.count()).thenReturn(10)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/games/count"))
            .andExpect(status().isOk)

    }

    @Test
    open fun update_WhenValidInput_ThenReturns200() {
        whenever(gameService.update(mockDto)).thenReturn(mockDto)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$url/game").content(
                gson.toJson(mockDto)
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun update_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.update(mockDto)).thenReturn(mockDto)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$url/game").content(
                "Bad Input"
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

    @Test
    open fun update_WhenUnknownId_ThenReturns404() {
        whenever(gameService.update(mockDto)).thenReturn(null)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$url/game").content(
                gson.toJson(mockDto)
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)

    }

    @Test
    open fun listAll_WhenCalled_ThenReturns200() {

        whenever(gameService.listAll()).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games").content(
                gson.toJson(mockDto)
            )
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun paginate_WhenValidInput_ThenReturns200() {

        whenever(gameService.paginate(anyInt(), anyInt())).thenReturn(listOf(mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?page=1&count=2").content(
                gson.toJson(mockDto)
            )
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun paginate_WhenInvalidInput_ThenReturns400() {

        whenever(gameService.paginate(anyInt(), anyInt())).thenReturn(listOf(mockDto, mockDto))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?page=Bad&count=2").content(
                gson.toJson(mockDto)
            )
        )
            .andExpect(status().isBadRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?page=1&count=Input").content(
                gson.toJson(mockDto)
            )
        )
            .andExpect(status().isBadRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?page=Bad&count=Input").content(
                gson.toJson(mockDto)
            )
        )
            .andExpect(status().isBadRequest)

    }

    @Test
    open fun deleteAll_WhenCalled_ThenReturns200() {
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/games"))
            .andExpect(status().isOk)

    }

    @Test
    open fun createMultiple_WhenValidInput_ThenReturns200() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games")
                .content(gson.toJson(listOf(mockDto, mockDto, mockDto, mockDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun createMultiple_WhenInvalidInput_ThenReturns400() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games")
                .content("Bad Input")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }


    @Test
    open fun findByName_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByName(anyString())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?name='test'")
                .content(gson.toJson(listOf(mockDto, mockDto, mockDto, mockDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun findByPublisher_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByPublisher(anyString())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?name='test'")
                .content(gson.toJson(listOf(mockDto, mockDto, mockDto, mockDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun findByPrice_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByPrice(anyDouble())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?price=0.2")
                .content(gson.toJson(listOf(mockDto, mockDto, mockDto, mockDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun findByPrice_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.findByPrice(anyDouble())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?price=100else0.123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

    @Test
    open fun findByRating_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByRating(anyInt())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?rating=1")
                .content(gson.toJson(listOf(mockDto, mockDto, mockDto, mockDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    open fun findByRating_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.findByRating(anyInt())).thenReturn(listOf(mockDto, mockDto, mockDto, mockDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?rating=100else0.123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

}