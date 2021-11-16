package com.commerzbank.VideoGameDatabase

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

open class Helper {
    companion object {
        fun <T> whenever(methodCall: T): OngoingStubbing<T> =
            Mockito.`when`(methodCall)
    }


}