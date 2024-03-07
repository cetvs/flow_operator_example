package Async.`3`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.*

class View {

    var productList = mutableListOf<ProductInfo>()
    val viewModel = ViewModel()

    fun startAsync1() {
        println("ассинхроно")
        val start = Date().time

        viewModel.viewModelCoroutineScope.launch {
            viewModel.productStateFlow.collect {
                productList = it.toMutableList()
                showProductList()
                showTime(start)
            }
        }
        viewModel.getProductAsync()
    }

    fun start1() {
        println("не ассинхроно")
        val start = Date().time

        viewModel.viewModelCoroutineScope.launch {
            viewModel.productStateFlow.collect {
                productList = it.toMutableList()
                showProductList()
                showTime(start)
            }
        }
        viewModel.getProduct()
    }

    private fun showTime(start: Long) {
        println("Время выполнения = ${Date().time - start} мс")
    }

    private fun showProductList() {
        println("Список товаров: $productList")
    }
}