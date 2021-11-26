package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.Helper.Companion.whenever
import com.commerzbank.VideoGameDatabase.controller.GameController
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.dto.UpdateGameDto
import com.commerzbank.VideoGameDatabase.mappers.GameMapper.Companion.mapper

import com.commerzbank.VideoGameDatabase.service.GameService
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner

import org.springframework.boot.test.mock.mockito.MockBean

import org.mockito.ArgumentMatchers.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration


import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders




@WebMvcTest
class GameControllerTests (@Autowired val mockMvc: MockMvc) {


    val url = "/api/v1"
    val gson = Gson()


    @MockBean
    lateinit var gameService: GameService

    private val sampleDto = GameDto(1, "testName", "testPublisher", 1, 1.0)




    @Test
    fun create_WhenValidInput_ThenReturns200() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games")
                .content(gson.toJson(sampleDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful)
    }

    @Test
    fun create_WhenInvalidInput_ThenReturns400() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games")
                .content("Bad Input")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun get_WhenValidInput_ThenReturns200() {
        whenever(gameService.get(anyInt())).thenReturn(sampleDto)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/games/1")).andExpect(jsonPath("name").value("testName"))
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("publisher").value("testPublisher"))
            .andExpect(jsonPath("rating").value(1))
            .andExpect(jsonPath("price").value(1.0))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun get_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.get(anyInt())).thenReturn(sampleDto)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/games/mockBadRequest"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun get_WhenUnknownId_ThenReturns404() {
        whenever(gameService.get(anyInt())).thenReturn(null)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/games/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun delete_WhenValidInput_ThenReturns200() {
        whenever(gameService.delete(anyInt())).thenReturn(sampleDto)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/games/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun delete_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.delete(anyInt())).thenReturn(sampleDto)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/games/mockBadRequest"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun delete_WhenUnknownId_ThenReturns404() {
        whenever(gameService.delete(anyInt())).thenReturn(null)
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/games/1"))
            .andExpect(status().isNotFound)
    }


    @Test
    fun count_WhenCalled_ThenReturns200() {
        whenever(gameService.count()).thenReturn(10)
        mockMvc.perform(MockMvcRequestBuilders.get("$url/games/count"))
            .andExpect(status().isOk)

    }

    @Test
    fun update_WhenValidInput_ThenReturns200() {
        whenever(gameService.exists(anyInt())).thenReturn(true)
        mockMvc.perform(

            MockMvcRequestBuilders.put("$url/games/1").content(
                gson.toJson(mapper.dtoToUpdate(sampleDto))
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().is2xxSuccessful)

    }

    @Test
    fun update_WhenInvalidInput_ThenReturns400() {

        mockMvc.perform(
            MockMvcRequestBuilders.put("$url/games/1").content(
                "Bad Input"
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

    @Test
    fun update_WhenUnknownId_ThenReturns404() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("$url/games/1").content(
                gson.toJson(sampleDto)
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun listAll_WhenCalled_ThenReturns200() {

        whenever(gameService.listAll()).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games").content(
                gson.toJson(sampleDto)
            )
        )
            .andExpect(status().isOk)

    }

    @Test
    fun paginate_WhenValidInput_ThenReturns200() {

        whenever(gameService.listAll(anyInt(), anyInt())).thenReturn(listOf(sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?page=1&count=2").content(
                gson.toJson(sampleDto)
            )
        )
            .andExpect(status().isOk)

    }



    @Test
    fun deleteAll_WhenCalled_ThenReturns200() {
        mockMvc.perform(MockMvcRequestBuilders.delete("$url/games/batch"))
            .andExpect(status().isOk)

    }

    @Test
    fun createMultiple_WhenValidInput_ThenReturns200() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games/import")
                .content(gson.toJson(listOf(sampleDto, sampleDto, sampleDto, sampleDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    fun createMultiple_WhenInvalidInput_ThenReturns400() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("$url/games/import")
                .content("Bad Input")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }


    @Test
    fun findByName_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByName(anyString())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?name='test'")
                .content(gson.toJson(listOf(sampleDto, sampleDto, sampleDto, sampleDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    fun findByPublisher_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByPublisher(anyString())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?name='test'")
                .content(gson.toJson(listOf(sampleDto, sampleDto, sampleDto, sampleDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
   fun findByPrice_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByPrice(anyDouble())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?price=0.2")
                .content(gson.toJson(listOf(sampleDto, sampleDto, sampleDto, sampleDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    fun findByPrice_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.findByPrice(anyDouble())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?price=100else0.123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

    @Test
    fun findByRating_WhenValidInput_ThenReturns200() {
        whenever(gameService.findByRating(anyInt())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?rating=1")
                .content(gson.toJson(listOf(sampleDto, sampleDto, sampleDto, sampleDto)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

    }

    @Test
    fun findByRating_WhenInvalidInput_ThenReturns400() {
        whenever(gameService.findByRating(anyInt())).thenReturn(listOf(sampleDto, sampleDto, sampleDto, sampleDto))
        mockMvc.perform(
            MockMvcRequestBuilders.get("$url/games?rating=100else0.123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

    }

}