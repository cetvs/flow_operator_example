package Async.`5`

import kotlinx.coroutines.launch
import java.util.*

class View {

    val viewModel = ViewModel()

    fun startAsync1() {
        println("ассинхроно")
        val start = Date().time

        viewModel.viewModelCoroutineScope.launch {
            launch {
                viewModel.saleInfo.collect {
                    saleInfoUi(it)
                }
            }
            launch {
                viewModel.userInfoState.collect {
                    it?.let { it.UserInfoUi() }
                }
            }
        }
        showTime(start)
        viewModel.getProductAsync()
    }

    fun start1() {
        println("не ассинхроно")
        val start = Date().time

//time        viewModel.viewModelCoroutineScope.launch {
//            viewModel.saleInfo.collect {
//                it?.let { saleInfoUi(it) }
//            }
//        }

    }

    private fun saleInfoUi(saleInfo: SaleInfo) {
        println(
            when (saleInfo) {
                is SaleInfo.SuccessSale -> "покупка может быть совершена \n баланс был: ${saleInfo.balance} \n на балансе останется: ${saleInfo.balance - saleInfo.salePrice}"
                is SaleInfo.CanSplit -> "можешь купить сплитом"
                is SaleInfo.NotEnough -> "нет средств на карте"
                is SaleInfo.None -> {
                    //none
                }
            }
        )
    }

    private fun showTime(start: Long) {
        println("Время выполнения = ${Date().time - start} мс")
    }

    private fun UserInfo.UserInfoUi() {
        println("Данные пользователя $this")
    }

}