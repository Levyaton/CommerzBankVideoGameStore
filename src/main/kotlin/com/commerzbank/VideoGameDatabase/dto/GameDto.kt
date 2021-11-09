package com.commerzbank.VideoGameDatabase.dto

import com.commerzbank.VideoGameDatabase.model.Game
import com.fasterxml.jackson.databind.JsonSerializer

data class GameDto(
                   var id: Int?,
                   var name:String,
                   var publisher: String,
                   var rating:Int,
                   var price:Double){
    constructor():
            this(null,"", "",0,0.0)
    constructor(game: Game):
            this(game.id,game.name,game.publisher,game.rating,game.price)

}

