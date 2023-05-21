package hu.webuni.andwallet.data

import androidx.lifecycle.LiveData
import androidx.room.*

data class SumBookingIncome(var sumAmount: Double)

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
    fun getSumIncome(): LiveData<SumBookingIncome>

    @Query(value = "SELECT SUM(amount * CASE income WHEN true THEN 1 WHEN false THEN -1 END) as balance FROM booking")
    fun getSumBalance(): Int
}