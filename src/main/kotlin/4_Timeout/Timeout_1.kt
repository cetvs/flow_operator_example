package `4_Timeout`

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking{
    withTimeout(1300L) {
        repeat(5) { i ->
            print("$i")
            delay(500)
        }
    }

    print("*")
}