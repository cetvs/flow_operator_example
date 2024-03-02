package `1_Scope`

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job: Job = launch {
        try {
            repeat(5) { i ->
                print("A$i")
                delay(108)

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