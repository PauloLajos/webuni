package hu.webinu.shoppinglist.data

data class ShoppingItem(
    var category: String,
    var name: String,
    var description: String,
    var estimatedPrice: Float,
    var boughtStatus: Boolean
)
