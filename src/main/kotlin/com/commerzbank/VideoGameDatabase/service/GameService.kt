
package com.commerzbank.VideoGameDatabase.service

import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import javax.xml.ws.ServiceMode


@Service
class GameService {
    @Autowired
    lateinit var dao: GameDao


    @Transactional
    fun get(id: Int): Game{
        return dao.getOne(id)
    }

    @Transactional
    fun listAll(): List<GameDto>{
        return dao.findAll().map {
            GameDto(it)
        }
    }


    @Transactional
    fun create(dto: GameDto){
        val game = Game(dto.name,dto.publisher,dto.rating,dto.price)
        dao.save(game)
    }


    @Transactional
    fun update(dto: GameDto){
        val game = get(dto.id!!)
        game.name = dto.name
        game.price = dto.price
        game.publisher = dto.publisher
        game.rating = dto.rating
        dao.save(game)
    }

    @Transactional
    fun delete(id: Int){
        dao.deleteById(id)
    }

    @Transactional
    fun findByName(name: String): List<GameDto>{
        return dao.findByName(name).map { GameDto(it) }
    }

    @Transactional
    fun findByRating(rating: Int): List<GameDto>{
        return dao.findByRating(rating).map { GameDto(it) }
    }
    @Transactional
    fun findByPrice(price: Double): List<GameDto>{
        return dao.findByPrice(price).map { GameDto(it) }
    }
    @Transactional
    fun findByPublisher(publisher: String): List<GameDto>{
        return dao.findByPublisher(publisher).map { GameDto(it) }
    }



}