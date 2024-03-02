package `4_Timeout`

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking(Dispatchers.Default){
    withTimeout(1300L) {
        repeat(5) { i ->
            println(Thread.currentThread().name)
            print("$i")
            delay(500)
        }
    }

    print("*")
}