package Async.`4`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModel {
    private val job = Job()
    val viewModelCoroutineScope = CoroutineScope(job)

    val mockApi = MockApi()

    private val _productStateFlow = MutableStateFlow(listOf<ProductInfo>())
    val productStateFlow = _productStateFlow

    private val _userInfoState = MutableStateFlow<UserInfo?>(null)
    val userInfoState = _userInfoState

    fun getProduct() = runBlocking {
        var products = mutableListOf<ProductInfo>()
        products.addAll(mockApi.mockProductInfo1())
        products.addAll(mockApi.mockProductInfo2())
        products.addAll(mockApi.mockProductInfo3())

        //aссинхронная фильтрация по наличию товара в регионе
        _userInfoState.value = mockApi.getUserinfo("my_user")
        val locationFilteredProduct = mutableListOf<ProductInfo>()
        for (product in products) {
            val location = mockApi.productLocation(product.id)
            if (userInfoState.value?.location in location){
                locationFilteredProduct.add(product)
            }
        }

        products = locationFilteredProduct

        //ассинхронная фильтрация по цене
        var res = mutableListOf<ProductInfo>()

        for (product in products) {
            val salePercent = mockApi.categorySaleByPercent(product.productType)
            val saleValue = mockApi.saleById(product.id)

            val resultPrice = if (product.price - saleValue < product.price * salePercent) {
                product.price - saleValue
            } else {
                product.price * salePercent
            }

            res.add(product.copy(price = resultPrice))
        }

        _productStateFlow.value = res
    }

    fun getProductAsync() {
        viewModelCoroutineScope.launch {
            val userInfo = async { mockApi.getUserinfo("my_user") }

            //получение данных
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

            //aссинхронная фильтрация по наличию товара в регионе
            _userInfoState.value = userInfo.await()

            var products = deferred1.await() + deferred2.await() + deferred3.await()
            val jobsLocation = mutableListOf<Job>()

            val locationFilteredProduct = mutableListOf<ProductInfo>()
            for (product in products) {
                jobsLocation.add(launch {
                    if (userInfoState.value?.location in mockApi.productLocation(product.id)) {
                        locationFilteredProduct.add(product)
                    }
                })
            }

            joinAll(*jobsLocation.toTypedArray())
            products = locationFilteredProduct

            //ассинхронная фильтрация по цене
            val hashMap = mutableMapOf(categoryAsync1.await(), categoryAsync2.await(), categoryAsync3.await())
            val jobsPrice = mutableListOf<Job>()
            val resList = mutableListOf<ProductInfo>()
            for (product in products) {
                jobsPrice.add(launch {
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

            joinAll(*jobsPrice.toTypedArray())
            _productStateFlow.value = resList.sortedBy { it.price }
        }
    }


}