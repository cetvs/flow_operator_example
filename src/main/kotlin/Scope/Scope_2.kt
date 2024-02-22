package Scope

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking(){
    val JobA = launch {
        delay(500)
        print("A")
    }

    coroutineScope {
        val JobB = launch {
            delay(1000)
            print("B")
        }
    }

    print("C")
}