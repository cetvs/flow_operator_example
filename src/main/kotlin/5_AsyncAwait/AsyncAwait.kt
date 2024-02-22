package `5_AsyncAwait`

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*


suspend fun performTask(id: Int): Int {
    delay(1000)
    return id * 2
}

fun main() = runBlocking {
    val start = Date().time


    val deferred1 = async { performTask(1) }
    val deferred2 = async { performTask(2) }

    print("A ")
    val result1 = deferred1.await()
    val result2 = deferred2.await()

    print("$result1 $result2 ${(Date().time - start)}")
}