package Scope

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.util.*

fun main(args: Array<String>) {
    val time = Date().time
    runBlocking {
        repeat(10) {
            launch {
                sleep(100)
                print("$it: ${Date().time - time}, ")
            }
        }
    }

    println("Result: ${Date().time - time}")
}