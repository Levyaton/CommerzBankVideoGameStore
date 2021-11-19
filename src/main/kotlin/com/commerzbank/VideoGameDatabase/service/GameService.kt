
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
        val entity = dto.toEntity()
        dao.save(entity)
    }


    @Transactional
    open fun update(dto: GameDto):GameDto?{
        val id = dto.id!!
        return if (dao.existsById(id)){
            val game = dao.getOne(id)
            game.name = dto.name.replace(("[^A-Za-z0-9 ]").toRegex(), "")
            game.price = dto.price
            game.publisher = dto.publisher.replace(("[^A-Za-z0-9 ]").toRegex(), "")
            game.rating = dto.rating
            dao.save(game)
            GameDto(game)
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
        return dao.findByName(name.replace(("[^A-Za-z0-9 ]").toRegex(), "")).map { GameDto(it) }
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
        return dao.findByPublisher(publisher.replace(("[^A-Za-z0-9 ]").toRegex(), "")).map { GameDto(it) }
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