package hu.webuni.andwallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking")
    fun getAllBooking(): List<BookingItem>

    @Insert
    fun insertBooking(vararg bookingData: BookingItem)

    @Delete
    fun deleteBooking(bookingData: BookingItem)
}