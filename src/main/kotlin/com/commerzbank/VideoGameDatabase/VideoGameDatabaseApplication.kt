package com.commerzbank.VideoGameDatabase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class VideoGameDatabaseApplication

fun main(args: Array<String>) {
	runApplication<VideoGameDatabaseApplication>(*args)
}
