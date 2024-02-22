package Scope

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val job: Job = launch {
        try {
            repeat(5) { i ->
                print("A$i")
                delay(108)
//                println(Thread.currentThread().name)
            }
        } finally {
            print("B")
        }
    }

    delay(257)
    print("C")
    job.cancelAndJoin()

    print("D")
}