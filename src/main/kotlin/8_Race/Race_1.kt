package `3_Exception`

import `8_Race`.Counter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

fun main() = runBlocking {
    val counter = Counter(0)

    var stateFlow = MutableStateFlow(0)

    val time = Date().time
    val jobs = mutableListOf<Job>()
    val customDispather = newFixedThreadPoolContext(2, "Treads: 2")

    repeat(1_000){
        jobs += launch(customDispather) {
            repeat(1_000){
                stateFlow.value++
            }
        }
    }

    println("Final count: ${stateFlow.value} in time: ${Date().time - time}")
}
