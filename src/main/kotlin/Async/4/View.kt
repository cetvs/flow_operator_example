package Async.`4`

import kotlinx.coroutines.launch
import java.util.*

class View {

    var productList = mutableListOf<ProductInfo>()
    val viewModel = ViewModel()

    fun startAsync1() {
        println("ассинхроно")
        val start = Date().time

        viewModel.viewModelCoroutineScope.launch {
            launch {
                viewModel.productStateFlow.collect {
                    productList = it.toMutableList()
                    showProductList()
                    showTime(start)
                }
            }
            launch {
                viewModel.userInfoState.collect {
                    it?.showUserInfo()
                }
            }
        }
        viewModel.getProductAsync()
    }

    fun start1() {
        println("не ассинхроно")
        val start = Date().time

        viewModel.viewModelCoroutineScope.launch {
            launch {
                viewModel.productStateFlow.collect {
                    productList = it.toMutableList()
                    showProductList()
                    showTime(start)
                }
            }
            launch {
                viewModel.userInfoState.collect {
                    it?.showUserInfo()
                }
            }
        }
        viewModel.getProduct()
    }

    private fun UserInfo.showUserInfo() {
        println("Данные пользователя $this")
    }

    private fun showTime(start: Long) {
        println("Время выполнения = ${Date().time - start} мс")
    }

    private fun showProductList() {
        println("Список товаров: $productList")
    }
}