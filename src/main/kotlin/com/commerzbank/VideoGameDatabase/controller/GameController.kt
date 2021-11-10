package com.commerzbank.VideoGameDatabase.controller

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import kotlin.time.measureTime


@RestController
@RequestMapping("/game")
class GameController {

    @Autowired
    lateinit var service: GameService


    @GetMapping("/games")
    fun listAll():List<GameDto>{
        return service.listAll()
    }

   @GetMapping("/game")
    fun get(@RequestParam id: Int): Game {
        return service.get(id)
    }



    @PostMapping("/create")
    fun create(@RequestBody dto: GameDto){
        service.create(dto)
    }

    @GetMapping("/count")
    fun count(): Int{
        return service.listAll().size
    }


    @PostMapping("/update")
    fun update(@RequestBody dto: GameDto){
        service.update(dto)
    }


    @PostMapping("/delete")
    fun delete(@RequestParam id: Int){
        service.delete(id)
    }


    @GetMapping("/gamesWithName")
    fun findByName(@RequestParam name: String): List<GameDto>{
        return service.findByName(name)
    }


    @GetMapping("/gamesWithRating")
    fun findByRating(@RequestParam rating: Int): List<GameDto>{
        return service.findByRating(rating)
    }

    @GetMapping("/gamesWithPrice")
    fun findByPrice(@RequestParam price: Double): List<GameDto>{
        return service.findByPrice(price)
    }

    @GetMapping("/gamesWithPublisher")
    fun findByPublisher(@RequestParam publisher: String): List<GameDto>{
        return service.findByPublisher(publisher)
    }
}