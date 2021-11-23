package com.commerzbank.VideoGameDatabase.controller

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.dto.UpdateGameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.model.Game
import com.commerzbank.VideoGameDatabase.service.GameService
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger.getLogger
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import org.springframework.http.ResponseEntity






@RestController
@RequestMapping("/api/v1")

class GameController() {

    @Autowired constructor(
        service: GameService
    ) : this() {
        this.service = service
    }

    private lateinit var service: GameService

    @GetMapping("/games")
    fun listAll():List<GameDto>{
        return service.listAll()
    }

    @RequestMapping(value = ["/games"], params = ["page","size"], method = [RequestMethod.GET])
    fun listAll(@RequestParam page: Int, @RequestParam size: Int):List<GameDto>{
        return service.listAll(page,size)
    }



   @GetMapping("/games/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<GameDto> {
       val result = service.get(id) ?: return ResponseEntity<GameDto>(HttpStatus.NOT_FOUND)
       return ResponseEntity<GameDto>(result,HttpStatus.OK)


    }

    @DeleteMapping("/games/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<String> {
        if(service.delete(id)==null)
            return ResponseEntity<String>(HttpStatus.NOT_FOUND)
            Log.log.info("deleted id $id")
        return ResponseEntity<String>("Deleted game with id $id",HttpStatus.OK)

    }



    @PostMapping("/games/import")
    fun create(@RequestBody dto: GameDto){
        service.create(dto)
    }

    @PostMapping("/games/batch")
    fun createMultiple(@RequestBody dtos: List<GameDto>){
        for(dto in dtos)
            service.create(dto)
    }
    @GetMapping("/games/count")
    fun count(): Long{
        return service.count()
    }


    @PutMapping("/games/{id}")
    fun update(@RequestBody dto: UpdateGameDto, @PathVariable id: Int): ResponseEntity<String> {
        if( service.update(dto, id)==null)
            return ResponseEntity<String>(HttpStatus.NOT_FOUND)
        return ResponseEntity<String>("updated game with id $id",HttpStatus.OK)
    }

    @DeleteMapping("/games/batch")
    fun deleteAll(){
        service.dropAll()
    }


    @RequestMapping(value = ["/games"], params = ["name"], method = [RequestMethod.GET])
    fun findByName(@RequestParam name: String): List<GameDto>{
        return service.findByName(name)
    }

    @RequestMapping(value = ["/games"], params = ["name","page","size"], method = [RequestMethod.GET])
    fun findByName( @RequestParam name:String, @RequestParam page: Int, @RequestParam size: Int): List<GameDto>{
        return service.findByName(name,page,size)
    }


    @RequestMapping(value = ["/games"], params = ["rating"], method = [RequestMethod.GET])
    fun findByRating(@RequestParam rating: Int): List<GameDto>{
        return service.findByRating(rating)
    }

    @RequestMapping(value = ["/games"], params = ["rating","page","size"], method = [RequestMethod.GET])
    fun findByRating(@RequestParam rating: Int,@RequestParam page:Int, @RequestParam size: Int): List<GameDto>{
        return service.findByRating(rating,page,size)
    }

    @RequestMapping(value = ["/games"], params = ["price"], method = [RequestMethod.GET])
    fun findByPrice(@RequestParam price: Double): List<GameDto>{
        return service.findByPrice(price)
    }

    @RequestMapping(value = ["/games"], params = ["price","page","size"], method = [RequestMethod.GET])
    fun findByPrice(@RequestParam price: Double, @RequestParam page:Int, @RequestParam count: Int): List<GameDto>{
        return service.findByPrice(price,page,count)
    }

    @RequestMapping(value = ["/games"], params = ["publisher"], method = [RequestMethod.GET])
    fun findByPublisher(@RequestParam publisher: String): List<GameDto>{
        return service.findByPublisher(publisher)
    }

    @RequestMapping(value = ["/games"], params = ["publisher","page","size"], method = [RequestMethod.GET])
    fun findByPublisher(@RequestParam publisher: String, @RequestParam page:Int, @RequestParam size: Int): List<GameDto>{
        return service.findByPublisher(publisher,page,size)
    }
}