package hu.webuni.andwallet.data

import androidx.room.*

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking")
    fun getAllBooking(): List<BookingItem>

    @Insert
    fun insertBooking(vararg bookingData: BookingItem)

    @Delete
    fun deleteBooking(bookingData: BookingItem)

    @Query("DELETE FROM booking")
    fun deleteAll()

    @Query(value = "SELECT SUM(amount) as sumAmount FROM booking WHERE income = true")
    fun getSumIncome(): Int

    @Query(value = "SELECT SUM(amount) as sumAmount FROM booking WHERE income = false")
    fun getSumExpense(): Int

    @Query(value = "SELECT SUM(amount * CASE income WHEN true THEN 1 WHEN false THEN -1 END) as balance FROM booking")
    fun getSumBalance(): Int
}