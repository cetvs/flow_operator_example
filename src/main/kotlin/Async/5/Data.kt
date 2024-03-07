package Async.`5`

data class CardInfo(val number: Long, val balance: Long, val cashbackPercent: Long)

sealed class SaleInfo() {
    data class SuccessSale(val balance: Long, val salePrice: Long): SaleInfo()
    data class CanSplit(val balance: Long, val salePrice: Long): SaleInfo()
    data class NotEnough(val balance: Long): SaleInfo()
    object None: SaleInfo()
}