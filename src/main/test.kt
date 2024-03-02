
fun main(args: Array<String>) = runBlocking(Dispatchers.Default){


//    println(Thread.currentThread().name)
    val JobA = launch {
        delay(500)


        val JobB = launch {

            val JobE = launch {
                delay(1000)
                print("A")
            }
            print("F")

        }
    }

}