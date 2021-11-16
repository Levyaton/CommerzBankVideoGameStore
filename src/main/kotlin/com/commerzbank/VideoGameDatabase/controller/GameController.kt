package com.commerzbank.VideoGameDatabase.controller

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger.getLogger
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import org.springframework.http.ResponseEntity






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
        return service.paginate(page,count)
    }

   @GetMapping("/game/{id}")
   open fun get(@PathVariable id: Int): ResponseEntity<GameDto> {
       val result = service.get(id) ?: return ResponseEntity<GameDto>(HttpStatus.NOT_FOUND)
       return ResponseEntity<GameDto>(result,HttpStatus.OK)


    }

    @DeleteMapping("/game/{id}")
    open fun delete(@PathVariable id: Int): ResponseEntity<String> {
        if(service.delete(id)==null)
            return ResponseEntity<String>(HttpStatus.NOT_FOUND)
            Log.log.info("deleted id " + id.toString())
        return ResponseEntity<String>("Deleted game with id ${id}",HttpStatus.OK)

    }



    @PostMapping("/game")
    open fun create(@RequestBody dto: GameDto){
        service.create(dto)
    }

    @PostMapping("/games")
    open fun createMultiple(@RequestBody dtos: List<GameDto>){
        for(dto in dtos)
            service.create(dto)
    }
    @GetMapping("/games/count")
    open fun count(): Long{
        return service.count()
    }


    @PutMapping("/game")
    open fun update(@RequestBody dto: GameDto): ResponseEntity<String> {
        if( service.update(dto)==null)
            return ResponseEntity<String>(HttpStatus.NOT_FOUND)
        return ResponseEntity<String>("updated game with id ${dto.id}",HttpStatus.OK)
    }

    @DeleteMapping("/games")
    open fun deleteAll(){
        service.dropAll()
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