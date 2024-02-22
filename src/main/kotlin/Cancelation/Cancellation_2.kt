package Scope

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val supervisor = SupervisorJob()

    with(CoroutineScope(coroutineContext + supervisor)){
        val child = launch {
            try {
                print("A")
                delay(Long.MAX_VALUE)
            } finally {
                print("B")
            }
        }

        delay(1000)
        print("C")
        supervisor.cancel()
    }
    print("D")
}