package hu.webuni.andwallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingDAO {

    @Query("SELECT * FROM bookingData")
    fun getAllBooking(): List<BookingData>

    @Insert
    fun insertBooking(vararg bookingData: BookingData)

    @Delete
    fun deleteBooking(bookingData: BookingData)
}