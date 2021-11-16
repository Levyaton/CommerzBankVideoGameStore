
package com.commerzbank.VideoGameDatabase.service

import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.logger.Log
import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import javax.xml.ws.ServiceMode
import kotlin.math.log


@Service
open class GameService {
    @Autowired
    lateinit var dao: GameDao


    @Transactional
    open fun dropAll(){
        dao.deleteAll()
    }

    @Transactional
    open fun get(id: Int): GameDto?{
        if(dao.existsById(id))
            return GameDto(dao.getOne(id))
        return null
    }

    @Transactional
    open fun listAll(): List<GameDto>{
        return dao.findAll().map {
            GameDto(it)
        }
    }

    @Transactional
    open fun paginate(page:Int,count:Int):List<GameDto>{
        val games = listAll()
        val list = mutableListOf<GameDto>()
        val index = (page-1)*count
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

    @Transactional
    open fun create(dto: GameDto){
        dao.save(dto.toEntity())
    }


    @Transactional
    open fun update(dto: GameDto):GameDto?{
        val id = dto.id!!
        return if (dao.existsById(id)){
            val game = dao.getOne(id)
            game.name = dto.name
            game.price = dto.price
            game.publisher = dto.publisher
            game.rating = dto.rating
            dao.save(game)
            dto
        } else{
            null
        }
    }

    @Transactional
    open fun delete(id: Int):GameDto?{
        if(dao.existsById(id)){
            val game = dao.getOne(id)
            dao.deleteById(id)
            return GameDto(game)
        }
        return null


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

    @Transactional
    open fun exists(id:Int):Boolean{
        return dao.existsById(id)
    }

    @Transactional
    open fun count(): Long {
        return dao.count()
    }


}