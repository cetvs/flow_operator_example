package Scope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking{
    val JobA = launch {
        delay(500)
        print("A")
    }

    val JobB = launch {
        delay(1000)
        print("B")
    }

    print("C")
}