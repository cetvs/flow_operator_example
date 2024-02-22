package Exception

import Race.Counter
import kotlinx.coroutines.*
import java.util.*

fun main() = runBlocking {
    val counter = Counter(0)
    val time = Date().time
    val jobs = mutableListOf<Job>()
    val customDispather = newFixedThreadPoolContext(2, "Treads: 2")

    repeat(1_000){
        jobs += launch(customDispather) {
            repeat(1_000){
                counter.count++
            }
        }
    }

    println("Final count: ${counter.count} in time: ${Date().time - time}")
}

//ACBEROR