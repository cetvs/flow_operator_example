package Exception

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler{_, _ -> print("error") }

    val job = GlobalScope.launch(exceptionHandler) {
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