package Async.`1`

import kotlinx.coroutines.delay

class MockApi {

    suspend fun mockProductInfo1(): List<ProductInfo> {
        delay(1000L)
        return mockDataProductInfo1
    }

    suspend fun mockProductInfo2(): List<ProductInfo> {
        delay(1200L)
        return mockDataProductInfo2
    }

    suspend fun mockProductInfo3(): List<ProductInfo> {
        delay(1300L)
        return mockDataProductInfo3
    }

    companion object {
        val mockDataProductInfo1 = listOf(
            ProductInfo(
                name = "паста для зубов",
                productType = "гигиена",
                price = 200,
            ),
            ProductInfo(
                name = "Крем для лица",
                productType = "гигиена",
                price = 100,
            ),
            ProductInfo(
                name = "Щетка зубная",
                productType = "гигиена",
                price = 80,
            ),
        )

        val mockDataProductInfo2 = listOf(
            ProductInfo(
                name = "Oreo",
                productType = "еда",
                price = 125,
            ),
            ProductInfo(
                name = "Сок апельсиновый",
                productType = "еда",
                price = 120,
            ),
            ProductInfo(
                name = "Хлеб черный",
                productType = "еда",
                price = 30,
            ),
        )

        val mockDataProductInfo3 = listOf(
            ProductInfo(
                name = "Playstation 5",
                productType = "техника",
                price = 50_000,
            ),
            ProductInfo(
                name = "Кофе машина",
                productType = "техника",
                price = 10_000,
            ),
        )
    }
}