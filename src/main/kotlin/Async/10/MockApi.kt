package Async.`10`

import java.util.*

class MockApi {

    fun getNamebyId() {
        val randomString = UUID.randomUUID().toString().substring(0, 3)
        println("$randomString")
    }

    companion object {
//        val setMap = mapOf()
    }
}