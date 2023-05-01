package hu.webinu.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM ShoppingItem")
    fun getAllExamples(): List<ShoppingItem>

    @Query("SELECT * FROM ShoppingItem WHERE category = :category")
    fun getSomeExamples(category: String): List<ShoppingItem>

    @Insert
    fun insertItems(vararg examples: ShoppingItem)

    @Delete
    fun deleteItem(example: ShoppingItem)
}