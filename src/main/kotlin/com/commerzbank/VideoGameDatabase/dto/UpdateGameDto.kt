package com.commerzbank.VideoGameDatabase.dto

import com.commerzbank.VideoGameDatabase.model.Game
import com.fasterxml.jackson.databind.JsonSerializer

data class UpdateGameDto(
                   var name:String,
                   var publisher: String,
                   var rating:Int,
                   var price:Double){
    constructor():
            this("", "",0,0.0)
    constructor(game: Game):
            this(game.name,game.publisher,game.rating,game.price)

    fun toEntity(): Game{
        return Game(name,publisher,rating, price)
    }



}

