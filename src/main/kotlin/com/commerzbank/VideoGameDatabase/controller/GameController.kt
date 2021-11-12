package com.commerzbank.VideoGameDatabase.controller

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger.getLogger
import javax.transaction.Transactional



@RestController
@RequestMapping("/api/v1")
open class GameController {


    @Autowired
    lateinit var service: GameService


    @GetMapping("/games")
    open fun listAll():List<GameDto>{
        return service.listAll()
    }

    @RequestMapping(value = ["/games"], params = ["page","count"])
    open fun paginate(@RequestParam page: Int, @RequestParam count: Int): List<GameDto>{
        val games = service.listAll()
        val list = mutableListOf<GameDto>()
        var index = (page-1)*count
        for (x in index until page*count){
            if(x > games.size-1){
                return list
            }
            else{
                list.add(games[x])
            }
        }
        Log.log.info(list.size.toString())
        return list
    }

   @GetMapping("/game/{id}")
   open fun get(@PathVariable id: Int): GameDto {
        return service.get(id)
    }

    @DeleteMapping("/game/{id}")
    open fun delete(@PathVariable id: Int){
        service.delete(id)
        Log.log.info("deleted id " + id.toString())
    }



    @PostMapping("/game")
    open fun create(@RequestBody dto: GameDto){
        service.create(dto)
    }

    @PostMapping("/games")
    open fun create(@RequestBody dtos: List<GameDto>){
        for(dto in dtos)
            service.create(dto)
    }
    @GetMapping("/games/count")
    open fun count(): Int{
        return service.listAll().size
    }


    @PutMapping("/game")
    open fun update(@RequestBody dto: GameDto){
        service.update(dto)
    }




    @RequestMapping(value = ["/games"], params = ["name"])
    open fun findByName(@RequestParam name: String): List<GameDto>{
        return service.findByName(name)
    }


    @RequestMapping(value = ["/games"], params = ["rating"])
    open fun findByRating(@RequestParam rating: Int): List<GameDto>{
        return service.findByRating(rating)
    }

    @RequestMapping(value = ["/games"], params = ["price"])
    open fun findByPrice(@RequestParam price: Double): List<GameDto>{
        return service.findByPrice(price)
    }

    @RequestMapping(value = ["/games"], params = ["publisher"])
    open fun findByPublisher(@RequestParam publisher: String): List<GameDto>{
        return service.findByPublisher(publisher)
    }
}