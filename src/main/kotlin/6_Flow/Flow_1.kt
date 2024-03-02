package `1_Scope`

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


suspend fun performRequest(request: Int): String {
    delay(100)
    if (request == 2) throw RuntimeException("error")
    return "$request"
}

fun requestFlow() = flow {
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking {
    requestFlow()
        .map { request -> performRequest(request) }
        .catch { e -> emit( e.localizedMessage) }
        .collect{ response -> print(response) }
    print("C")
}