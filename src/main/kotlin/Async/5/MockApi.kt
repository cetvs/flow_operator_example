package Async.`5`

import kotlinx.coroutines.delay

class MockApi {

    suspend fun getUserinfo(token: String): UserInfo {
        delay(700L)
        return if (token == "my_user") userInfo else otherUserInfo
    }

    suspend fun saleById(id: Long): Long {
        delay(500)
        return when (id) {
            1L -> 110
            2L -> 20
            3L -> 200
            else -> 0
        }
    }

    suspend fun getCardsInfo(id: Long): List<CardInfo> {
        return when (id) {
            1L -> listOf(
                CardInfo(
                    number = 1,
                    balance = 100000,
                    cashbackPercent = 5
                ),
                CardInfo(
                    number = 2,
                    balance = 1300,
                    cashbackPercent = 10
                ),
                CardInfo(
                    number = 3,
                    balance = 5400,
                    cashbackPercent = 10
                )
            )
            else -> listOf()
        }
    }

    suspend fun getProductCardById(id: Long): List<ProductInfo> {
        return when (id) {
            1L -> {
                delay(1000L)
                mockDataProductInfo
            }
            else -> {
                delay(1000L)
                listOf()
            }
        }
    }

    companion object {
        val userInfo = UserInfo(1,"Sergey", Location.Voronezh)

        val otherUserInfo = UserInfo(0,"Admin", Location.Moscow)

        val mockDataProductInfo = listOf(
            ProductInfo(
                id = 1,
                name = "Плакат",
                price = 800,
            ),
            ProductInfo(
                id = 2,
                name = "Носки с рисунком",
                price = 600,
            ),
            ProductInfo(
                id = 3,
                name = "Щетка зубная",
                price = 100,
            ),
            ProductInfo(
                id = 4,
                name = "Стиральный порошок",
                price = 300,
            ),
            ProductInfo(
                id = 5,
                name = "игрушка Fanko pop",
                price = 2000,
            ),
        )
    }
}