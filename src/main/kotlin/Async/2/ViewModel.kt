package Async.`2`

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

            val res1 = async {
                val list = deferred1.await()
                val salePercent = if (list.isNotEmpty()) {
                    mockApi.categorySaleByPercent(deferred1.await()[0].productType)
                } else 0

                val result = mutableListOf<ProductInfo>()

                for (product in list) {
                    val saleValue = mockApi.saleById(product.id)
                    val resultPrice = if (product.price - saleValue < product.price * salePercent) {
                        product.price - saleValue
                    } else {
                        product.price * salePercent
                    }
                    result.add(product.copy(price = resultPrice))
                }
                return@async result
            }

            val res2 = async {
                val list = deferred2.await()
                val salePercent = if (list.isNotEmpty()) {
                    mockApi.categorySaleByPercent(list[0].productType)
                } else 0

                val result = mutableListOf<ProductInfo>()

                for (product in list) {
                    launch {
                        val saleValue = mockApi.saleById(product.id)
                        val resultPrice = if (product.price - saleValue < product.price * salePercent) {
                            product.price - saleValue
                        } else {
                            product.price * salePercent
                        }
                        result.add(product.copy(price = resultPrice))
                    }
                }
                return@async result
            }

            val res3 = async {
                val list = deferred3.await()
                val salePercent = if (list.isNotEmpty()) {
                    mockApi.categorySaleByPercent(list[0].productType)
                } else 0

                val result = mutableListOf<ProductInfo>()

                for (product in list) {
                    val saleValue = mockApi.saleById(product.id)
                    val resultPrice = if (product.price - saleValue < product.price * salePercent) {
                        product.price - saleValue
                    } else {
                        product.price * salePercent
                    }
                    result.add(product.copy(price = resultPrice))
                }
                return@async result
            }

            val resultProductWithNewPriceList = res1.await() + res2.await() + res3.await()

            productStateFlow.value = resultProductWithNewPriceList.sortedBy { it.price }

        }
    }
}