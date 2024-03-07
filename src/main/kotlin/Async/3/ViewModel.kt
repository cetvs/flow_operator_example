package Async.`3`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

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

        val res = mutableListOf<ProductInfo>()

        for (product in list) {
            val salePercent = mockApi.categorySaleByPercent(product.productType)
            val saleValue = mockApi.saleById(product.id)

            val resultPrice = if (product.price - saleValue < product.price * salePercent) {
                product.price - saleValue
            } else {
                product.price * salePercent
            }

            res.add(product.copy(price = resultPrice))
        }

        _productStateFlow.value = list
    }

    fun getProductAsync() {
        viewModelCoroutineScope.launch {
            val deferred1 = async { mockApi.mockProductInfo1() }
            val deferred2 = async { mockApi.mockProductInfo2() }
            val deferred3 = async { mockApi.mockProductInfo3() }

            val categoryAsync1 = async {
                val productType = deferred1.await()[0].productType
                return@async Pair(productType, mockApi.categorySaleByPercent(productType))
            }
            val categoryAsync2 = async {
                val productType = deferred2.await()[0].productType
                return@async Pair(productType, mockApi.categorySaleByPercent(productType))
            }
            val categoryAsync3 = async {
                val productType = deferred3.await()[0].productType
                return@async Pair(productType, mockApi.categorySaleByPercent(productType))
            }
            val products = deferred1.await() + deferred2.await() + deferred3.await()
            val hashMap = mutableMapOf(categoryAsync1.await(), categoryAsync2.await(), categoryAsync3.await())

            val jobs = mutableListOf<Job>()
            val resList = mutableListOf<ProductInfo>()

            for (product in products) {
                jobs.add(launch {
                    val salePercent = hashMap[product.productType] ?: 0
                    val saleValue = mockApi.saleById(product.id)

                    val res = if (product.price - saleValue < product.price * salePercent) {
                        product.price - saleValue
                    } else {
                        product.price * salePercent
                    }
                    resList.add(product.copy(price = res))
                })
            }

            joinAll(*jobs.toTypedArray())
            _productStateFlow.value = resList.sortedBy { it.price }
        }
    }
}