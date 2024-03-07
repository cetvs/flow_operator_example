package Async.`10`

import kotlinx.coroutines.launch
import java.util.*

class View {

    val mockApi = MockApi()

    fun startAsync() {
        mockApi.getNamebyId()
    }

    fun startThread() {
        mockApi.getNamebyId()
    }

}