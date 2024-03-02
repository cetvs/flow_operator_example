package `6_Flow`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

private fun stringFlow(): Flow<String> = flow {
    ('A'..'E').forEach { char ->
        emit("$char->")
        delay(1000)
    }
}

fun main() = runBlocking{
    val time = Date().time
    var result = ""

    stringFlow().flowOn(Dispatchers.IO).map { item ->
        delay(500)
        item
    }.flowOn(Dispatchers.Default).collect{ item ->
        result += item
    }

    print("Result: $result ${ Date().time - time }")
}