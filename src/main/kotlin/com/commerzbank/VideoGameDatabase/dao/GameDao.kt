package com.commerzbank.VideoGameDatabase.dao

import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository


@Repository
interface GameDao: PagingAndSortingRepository<Game, Int>, JpaRepository<Game, Int> {
    fun findByName(name: String): List<Game>
    fun findByRating(id: Int): List<Game>
    fun findByPrice(price: Double): List<Game>
    fun findByPublisher(publisher:String): List<Game>

    fun listAll(page:Pageable): List<Game>
    fun findByName(name: String, page: Pageable): List<Game>
    fun findByRating(id: Int, page: Pageable): List<Game>
    fun findByPrice(price: Double, page: Pageable): List<Game>
    fun findByPublisher(publisher:String, page: Pageable): List<Game>
}