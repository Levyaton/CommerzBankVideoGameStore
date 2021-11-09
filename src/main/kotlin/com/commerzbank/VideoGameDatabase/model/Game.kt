package com.commerzbank.VideoGameDatabase.model


import javax.persistence.*


@Entity
@NamedQueries(
        NamedQuery(name = "Game.findByPublisher", query = "SELECT Game.publisher FROM Game u WHERE Game.publisher= ?1"),
        NamedQuery(name = "Game.findByName", query = "SELECT Game.name FROM Game u WHERE Game.name= ?1"),
        NamedQuery(name = "Game.findByRating", query = "SELECT Game.rating FROM Game u WHERE Game.rating= ?1"),
        NamedQuery(name = "Game.findByPrice", query = "SELECT Game.price FROM Game u WHERE Game.price= ?1"))
data class Game(
                var name:String,
                var publisher: String,
                var rating:Int,
                var price:Double
) {
    constructor():
            this("","",0,0.0)
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
}