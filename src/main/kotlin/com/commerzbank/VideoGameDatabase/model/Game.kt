package com.commerzbank.VideoGameDatabase.model


import javax.persistence.*


@Entity
@NamedQueries(
        NamedQuery(name = "Game.findByPublisher", query = "SELECT g FROM Game g WHERE g.publisher= ?1"),
        NamedQuery(name = "Game.findByName", query = "SELECT g FROM Game g WHERE g.name= ?1"),
        NamedQuery(name = "Game.findByRating", query = "SELECT g FROM Game g WHERE g.rating= ?1"),
        NamedQuery(name = "Game.findByPrice", query = "SELECT g FROM Game g WHERE g.price= ?1"))
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