package Async.`1`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class ViewModel {
    private val job = Job()
    val viewModelCoroutineScope = CoroutineScope(job)

    val mockApi = MockApi()

    private val _productStateFlow = MutableStateFlow(listOf<ProductInfo>())
    val productStateFlow = _productStateFlow

    fun getProduct() = runBlocking {
        val list = mutableListOf<ProductInfo>()
        list.addAll(mockApi.mockProductInfo1())
        list.addAll(mockApi.mockProductInfo2())
        list.addAll(mockApi.mockProductInfo3())
        _productStateFlow.value = list
    }

    fun getProductAsync() {
        viewModelCoroutineScope.launch {
            val start = Date().time
            val deferred1 = async { mockApi.mockProductInfo1() }
            val deferred2 = async { mockApi.mockProductInfo2() }
            val deferred3 = async { mockApi.mockProductInfo3() }
            productStateFlow.value = deferred1.await() + deferred2.await() + deferred3.await()
        }
    }
}