package com.commerzbank.VideoGameDatabase.logger

import org.slf4j.LoggerFactory

open class Log {
    companion object{
        val log = LoggerFactory.getLogger(Log::class.java)
    }

}