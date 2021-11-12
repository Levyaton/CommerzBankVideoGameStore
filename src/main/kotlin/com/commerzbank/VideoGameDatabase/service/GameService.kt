
package com.commerzbank.VideoGameDatabase.service

import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import javax.xml.ws.ServiceMode
import kotlin.math.log


@Service
open class GameService {
    @Autowired
    lateinit var dao: GameDao


    @Transactional
    open fun get(id: Int): GameDto{
        return try{
            GameDto(dao.getOne(id))
        } catch (e: Exception){
            Log.log.error(e.message)
            GameDto()
        }
    }

    @Transactional
    open fun listAll(): List<GameDto>{
        return dao.findAll().map {
            GameDto(it)
        }
    }


    @Transactional
    open fun create(dto: GameDto){
        val game = Game(dto.name,dto.publisher,dto.rating,dto.price)
        dao.save(game)
    }


    @Transactional
    open fun update(dto: GameDto){
        val game = dao.getOne(dto.id!!)
        game.name = dto.name
        game.price = dto.price
        game.publisher = dto.publisher
        game.rating = dto.rating
        dao.save(game)
    }

    @Transactional
    open fun delete(id: Int){
        dao.deleteById(id)
    }

    @Transactional
    open fun findByName(name: String): List<GameDto>{
        val named = dao.findByName(name)
        val list = mutableListOf<GameDto>()
        for(enity in named){
            list.add(GameDto(enity))
        }
        return dao.findByName(name).map { GameDto(it) }
    }

    @Transactional
    open fun findByRating(rating: Int): List<GameDto>{
        return dao.findByRating(rating).map { GameDto(it) }
    }
    @Transactional
    open fun findByPrice(price: Double): List<GameDto>{
        return dao.findByPrice(price).map { GameDto(it) }
    }
    @Transactional
    open fun findByPublisher(publisher: String): List<GameDto>{
        return dao.findByPublisher(publisher).map { GameDto(it) }
    }



}