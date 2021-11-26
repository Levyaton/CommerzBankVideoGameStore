package com.commerzbank.VideoGameDatabase.dto

import com.commerzbank.VideoGameDatabase.model.Game
import com.fasterxml.jackson.databind.JsonSerializer


data class GameDto(
                   var id: Int = -1000,
                   var name:String = "",
                   var publisher: String = "",
                   var rating:Int = 0,
                   var price:Double = 0.0)



