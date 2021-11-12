package com.commerzbank.VideoGameDatabase

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.model.Game
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.bind.Bindable.listOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.junit4.SpringRunner
import java.net.URI

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate
    @Test
    fun testGameController(){
    //    val result = testRestTemplate.getForEntity("/hello/string", String::class)
    }
}