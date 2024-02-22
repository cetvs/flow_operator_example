package Exception

import kotlinx.coroutines.*

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, _ -> print("error") }
    val scope = CoroutineScope(Job())


    val child1 = scope.launch {
        print("A")
        delay(500)
        print("B")
        throw RuntimeException()
    }

    val child2 = scope.launch(Job()) {
        print("C")
        delay(1000)
        print("*")
    }

    joinAll(child1, child2)

    print("D")
}

