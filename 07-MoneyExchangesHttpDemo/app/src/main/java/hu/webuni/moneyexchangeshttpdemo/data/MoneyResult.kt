package hu.webuni.moneyexchangeshttpdemo.data

// JSON to Data class:
// https://http4k-data-class-gen.herokuapp.com/
// https://github.com/wuseal/JsonToKotlinClass
// * https://jsonformatter.org/json-to-kotlin

data class MoneyResult (
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
    )

data class Info (
    val rate: Double,
    val timestamp: Long
)

data class Query (
    val amount: Long,
    val from: String,
    val to: String
)