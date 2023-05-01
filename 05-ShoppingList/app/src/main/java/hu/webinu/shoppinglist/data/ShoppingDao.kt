package hu.webinu.shoppinglist.data

import androidx.room.*

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM ShoppingItem")
    fun getAllItems(): List<ShoppingItem>

    @Query("SELECT * FROM ShoppingItem WHERE category = :category")
    fun getSomeItems(category: String): List<ShoppingItem>

    @Insert
    fun insertItem(vararg shoppingItem: ShoppingItem)

    @Update
    fun updateItem(shoppingItem: ShoppingItem)

    @Delete
    fun deleteItem(shoppingItem: ShoppingItem)
}