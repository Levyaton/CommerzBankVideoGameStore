package com.commerzbank.VideoGameDatabase.model


import javax.persistence.*


@Entity
@NamedQueries(
        NamedQuery(name = "Game.findByPublisher", query = "SELECT publisher FROM Game u WHERE u.publisher= ?1"),
        NamedQuery(name = "Game.findByName", query = "SELECT name FROM Game u WHERE u.name= ?1"),
        NamedQuery(name = "Game.findByRating", query = "SELECT rating FROM Game u WHERE u.rating= ?1"),
        NamedQuery(name = "Game.findByPrice", query = "SELECT price FROM Game u WHERE u.price= ?1"))
class Game(
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