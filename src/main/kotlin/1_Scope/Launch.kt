package `1_Scope`

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking(Dispatchers.Default){


//    println(Thread.currentThread().name)
    val JobA = launch {
        delay(500)

        val JobB = launch {

            val JobE = launch {
                delay(1000)
                print("A")
            }
            print("F")

        }
    }

    print("FINISH ")
}