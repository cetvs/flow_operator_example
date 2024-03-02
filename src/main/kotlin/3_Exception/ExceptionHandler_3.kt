package `3_Exception`

import kotlinx.coroutines.*
import javax.naming.Context

fun main() = runBlocking(Dispatchers.Main) {
    val exceptionHandler = CoroutineExceptionHandler{_, _ -> print("error") }
    val scope = CoroutineScope(SupervisorJob())

    val job = scope.launch(exceptionHandler) {
        val child1 = launch {
            print("A")
            delay(500)
            print("B")
            throw RuntimeException()
        }

        val child2 = launch {
            print("C")
            delay(1000)
            print("*")
        }

        joinAll(child1, child2)
    }

    job.join()
}

//ACBEROR