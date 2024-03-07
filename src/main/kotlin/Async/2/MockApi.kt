package Async.`2`

import kotlinx.coroutines.delay

class MockApi {

    suspend fun categorySaleByPercent(productType: ProductType): Long {
        delay(500L)
        return when (productType) {
            ProductType.Hygiene -> 10
            ProductType.Food -> 15
            ProductType.Technique -> 20
        }
    }

    suspend fun saleById(id: Long): Long {
        delay(500)
        return when (id) {
            12L -> 11
            21L -> 20
            32L -> 2_000
            else -> 0
        }
    }

    suspend fun mockProductInfo1(): List<ProductInfo> {
        delay(1000L)
        return mockDataProductInfo1
    }

    suspend fun mockProductInfo2(): List<ProductInfo> {
        delay(1200L)
        return mockDataProductInfo2
    }

    suspend fun mockProductInfo3(): List<ProductInfo> {
        delay(1200L)
        return mockDataProductInfo3
    }

    companion object {
        val mockDataProductInfo1 = listOf(
            ProductInfo(
                id = 11,
                name = "паста для зубов",
                productType = ProductType.Hygiene,
                price = 200,
            ),
            ProductInfo(
                id = 12,
                name = "Крем для лица",
                productType = ProductType.Hygiene,
                price = 100,
            ),
            ProductInfo(
                id = 13,
                name = "Щетка зубная",
                productType = ProductType.Hygiene,
                price = 80,
            ),
        )

        val mockDataProductInfo2 = listOf(
            ProductInfo(
                id = 21,
                name = "Oreo",
                productType = ProductType.Food,
                price = 125,
            ),
            ProductInfo(
                id = 22,
                name = "Сок апельсиновый",
                productType = ProductType.Food,
                price = 120,
            ),
            ProductInfo(
                id = 23,
                name = "Хлеб черный",
                productType = ProductType.Food,
                price = 30,
            ),
        )

        val mockDataProductInfo3 = listOf(
            ProductInfo(
                id = 31,
                name = "Playstation 5",
                productType = ProductType.Technique,
                price = 50_000,
            ),
            ProductInfo(
                id = 32,
                name = "Кофе машина",
                productType = ProductType.Technique,
                price = 10_000,
            ),
        )
    }
}