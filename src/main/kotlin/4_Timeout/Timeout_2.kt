package `4_Timeout`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

fun numberFlow(): Flow<Int> = flow {
    repeat(3) {
        delay(100)
        emit(Random.nextInt(10))
    }
}

fun main(args: Array<String>) = runBlocking{
    withTimeoutOrNull(250) {
        numberFlow().collect {
            delay(50)
            print("$it")
        }
    }

    print("*")
}