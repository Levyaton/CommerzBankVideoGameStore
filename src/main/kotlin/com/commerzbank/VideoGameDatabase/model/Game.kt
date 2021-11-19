package com.commerzbank.VideoGameDatabase.model


import javax.persistence.*


@Entity
class Game(
    @Column(name="name", nullable=false)
                var name:String,
    @Column(name="publisher", nullable=false)
                var publisher: String,
    @Column(name="rating", nullable=false)
                var rating:Int,
    @Column(name="price", nullable=false)
                var price:Double
) {
    constructor():
            this("","",0,0.0)
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
}