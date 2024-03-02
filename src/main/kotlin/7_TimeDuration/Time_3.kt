package `1_Scope`

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

fun main(args: Array<String>) {
    runBlocking {
        val time = Date().time
        repeat(10) {
            launch {
                delay(100)
                print("$it: ${Date().time - time}, ")
            }
        }

        println("Result: ${Date().time - time}")
    }
}