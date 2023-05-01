package hu.webinu.shoppinglist.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ShoppingItem(
    var category: Int,
    var name: String,
    var description: String,
    var estimatedPrice: Float,
    var boughtStatus: Boolean
) : Serializable
