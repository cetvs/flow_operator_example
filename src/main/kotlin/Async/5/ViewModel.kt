package Async.`5`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModel {
    private val job = Job()
    val viewModelCoroutineScope = CoroutineScope(job)

    val mockApi = MockApi()

    private val _userInfoState = MutableStateFlow<UserInfo?>(null)
    val userInfoState = _userInfoState

    var cardsInfo = listOf<CardInfo>()

    private val _saleInfo = MutableStateFlow<List<ProductInfo>?>(null)
    val saleInfo: StateFlow<SaleInfo> = _saleInfo
        .map { productsList ->
            var bestCardInfo = CardInfo(0, 0, 0)

            var bestBalance = -1L
            var bestSplitBalance = -1L

            cardsInfo.forEach { cardInfo ->
                var balance = cardInfo.balance
                var splitBalance = cardInfo.balance * 4L

                productsList?.forEach {
                    val percent = (100 - cardInfo.cashbackPercent) * 0.01
                    val priceWithCashback = it.price - (percent * it.price).toLong()
                    balance -= priceWithCashback
                    splitBalance -= priceWithCashback
                }

                if (balance > bestBalance) {
                    bestBalance = balance
                    bestCardInfo = cardInfo
                }

                if (splitBalance > bestSplitBalance) {
                    bestSplitBalance = splitBalance
                    bestCardInfo = cardInfo
                }
            }

            return@map when {
                bestBalance > 0 -> SaleInfo.SuccessSale(bestCardInfo.balance, bestBalance)
                bestSplitBalance > 0 -> SaleInfo.CanSplit(bestCardInfo.balance, bestBalance)
                productsList != null -> SaleInfo.NotEnough(bestCardInfo.balance)
                else -> { SaleInfo.None }
            }
        }
        .stateIn(viewModelCoroutineScope, SharingStarted.Lazily, SaleInfo.None)

    fun getProductAsync() {
        viewModelCoroutineScope.launch {
            val userInfo = mockApi.getUserinfo("my_user")
            _userInfoState.value = userInfo

            val cardsInfoDef = async { mockApi.getCardsInfo(userInfo.id) }
            val productCartDef = async { mockApi.getProductCardById(userInfo.id) }
            cardsInfo = cardsInfoDef.await()
            _saleInfo.value = productCartDef.await()
        }
    }


}