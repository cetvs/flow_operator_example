import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

private fun stringFlow(): Flow<String> = flow {
    ('A'..'E').forEach { char ->
        emit("$char->")
        delay(50)
    }
}

fun main() = runBlocking{
    val time = Date().time
    var result = ""

    stringFlow().flatMapMerge { value ->
        flow {
            withContext(Dispatchers.IO){
                delay(100)
                emit(value)
            }
        }
    }.collect { item ->
        result += item
    }
    print("Result: $result ${time - Date().time   }")
}