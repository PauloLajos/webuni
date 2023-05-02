package hu.webinu.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) var Id: Long?,
    @ColumnInfo(name = "category") var category: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "estimatedPrice") var estimatedPrice: Float = 0f,
    @ColumnInfo(name = "boughtStatus") var boughtStatus: Boolean = false
) : Serializable
