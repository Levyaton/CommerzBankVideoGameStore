
package com.commerzbank.VideoGameDatabase.service

import com.commerzbank.VideoGameDatabase.dao.GameDao
import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.dto.UpdateGameDto
import com.commerzbank.VideoGameDatabase.mappers.GameMapper.Companion.mapper
import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import javax.xml.ws.ServiceMode
import kotlin.math.log


@Service
class GameService(){

    @Autowired constructor(
        dao: GameDao
    ) : this() {
        this.dao = dao
    }

    lateinit var dao:GameDao
    @Transactional
    fun dropAll(){
        dao.deleteAll()
    }

    @Transactional
    fun get(id: Int): GameDto?{
        if(dao.existsById(id))
            return mapper.entityToDTO(dao.getOne(id))
        return null
    }

    @Transactional
    fun listAll(): List<GameDto>{
        return dao.findAll().map {
            mapper.entityToDTO(it)
        }
    }

    @Transactional
    fun listAll(page: Int, size: Int): List<GameDto>{
        return dao.findAll(PageRequest.of(page,size)).content.map {
            mapper.entityToDTO(it)
        }
    }



    @Transactional
    fun create(dto: GameDto){
        val entity = mapper.dtoToEntity(dto)
        dao.save(entity)
    }


    @Transactional
    fun update(dto: UpdateGameDto, id: Int):GameDto?{
        return if (dao.existsById(id)){
            val game = dao.getOne(id)
            game.name = dto.name.replace(("[^A-Za-z0-9 ]").toRegex(), "")
            game.price = dto.price
            game.publisher = dto.publisher.replace(("[^A-Za-z0-9 ]").toRegex(), "")
            game.rating = dto.rating
            dao.save(game)
            mapper.entityToDTO(game)
        } else{
            null
        }
    }

    @Transactional
    fun delete(id: Int):GameDto?{
        if(dao.existsById(id)){
            val game = dao.getOne(id)
            dao.deleteById(id)
            return mapper.entityToDTO(game)
        }
        return null


    }

    @Transactional
    fun findByName(name: String): List<GameDto>{
        return dao.findByName(name.replace(("[^A-Za-z0-9 ]").toRegex(), "")).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByName(name: String, page: Int, size: Int): List<GameDto>{
        val regexedName = name.replace(("[^A-Za-z0-9 ]").toRegex(), "")
        return dao.findByName(regexedName, PageRequest.of(page, size)).map { mapper.entityToDTO(it) }
    }

    @Transactional
    fun findByRating(rating: Int, page: Int, size: Int): List<GameDto>{
        return dao.findByRating(rating, PageRequest.of(page, size)).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByRating(rating: Int): List<GameDto>{
        return dao.findByRating(rating).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByPrice(price: Double): List<GameDto>{
        return dao.findByPrice(price).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByPrice(price: Double, page: Int, size: Int): List<GameDto>{
        return dao.findByPrice(price, PageRequest.of(page, size)).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByPublisher(publisher: String): List<GameDto>{
        return dao.findByPublisher(publisher.replace(("[^A-Za-z0-9 ]").toRegex(), "")).map { mapper.entityToDTO(it) }
    }
    @Transactional
    fun findByPublisher(publisher: String, page: Int, size: Int): List<GameDto>{
        val regexedPublisher= publisher.replace(("[^A-Za-z0-9 ]").toRegex(), "")
        return dao.findByPublisher(regexedPublisher, PageRequest.of(page, size)).map { mapper.entityToDTO(it) }
    }

    @Transactional
    fun exists(id:Int):Boolean{
        return dao.existsById(id)
    }

    @Transactional
    fun count(): Long {
        return dao.count()
    }


}