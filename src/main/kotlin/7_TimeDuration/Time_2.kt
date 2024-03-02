package `1_Scope`

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.util.*

fun main(args: Array<String>) {
    var time = 0L
    runBlocking {
        time = Date().time
        repeat(10) {
            launch {
                sleep(100)
                print("$it: ${Date().time - time} ")
            }
        }
    }

    println(" Result: ${Date().time - time}")
}