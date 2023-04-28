package hu.webinu.shoppinglist.data

import android.os.Parcel
import android.os.Parcelable

data class ShoppingItem(
    var category: Int,
    var name: String,
    var description: String,
    var estimatedPrice: Float,
    var boughtStatus: Boolean
)
