package com.commerzbank.VideoGameDatabase.httpResponse

class ResponseBody() {
    var timestamp = ""
    var error = ""
    var message = ""
    var path = ""
    var status = 0

    constructor(timestamp: String, error: String, message: String, path: String, status: Int) : this() {
        this.timestamp = timestamp
        this.error = error
        this.message = message
        this.path = path
        this.status = status
    }
}