package hu.webinu.shoppinglist.data

import android.os.Parcel
import android.os.Parcelable

data class ShoppingItem(
    var category: String,
    var name: String,
    var description: String,
    var estimatedPrice: Float,
    var boughtStatus: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeFloat(estimatedPrice)
        parcel.writeByte(if (boughtStatus) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingItem> {
        override fun createFromParcel(parcel: Parcel): ShoppingItem {
            return ShoppingItem(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingItem?> {
            return arrayOfNulls(size)
        }
    }
}
