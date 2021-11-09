package com.commerzbank.VideoGameDatabase.dao

import com.commerzbank.VideoGameDatabase.model.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface GameDao: JpaRepository<Game,Int> {
    fun findByName(name: String): List<Game>
    fun findByRating(id: Int): List<Game>
    fun findByPrice(price: Double): List<Game>
    fun findByPublisher(publisher:String): List<Game>
}