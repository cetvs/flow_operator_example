package `1_Scope`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.util.*
import kotlin.coroutines.CoroutineContext

fun main(args: Array<String>) = runBlocking(){
    val time = Date().time

    val JobA = launch {
        print("A")
        delay(500)
        print("B")
    }

    val JobB = launch {
        print("C")
        sleep(1000)
        print("D")
    }

    val JobC = launch {
        print("E")
        delay(2000)
        print("F")
    }

    joinAll(JobA, JobB, JobC)

    print("Result: ${ Date().time - time }")
}