package hu.webuni.andwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webuni.andwallet.adapter.BookingAdapter
import hu.webuni.andwallet.data.BookingItem
import hu.webuni.andwallet.data.BookingDAO
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var bookingDao: BookingDAO
    private lateinit var bookingItemList: ArrayList<BookingItem>
    private lateinit var bookingAdapter: BookingAdapter

/*
    private lateinit var data: ShoppingDao
    private lateinit var itemList: ArrayList<ShoppingItem>
    private lateinit var shoppingAdapter: ShoppingAdapter

 */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        bookingItemList = ArrayList()
        bookingDao = AppDatabase.getInstance(this).bookingDao()

        Thread {
            if (bookingDao.getAllBooking().isEmpty()) {
                bookingDao.insertBooking(BookingItem(null, "Food",100,false))
                bookingDao.insertBooking(BookingItem(null, "Salary",200,true))
                bookingDao.insertBooking(BookingItem(null, "Wear",150,false))
                bookingDao.insertBooking(BookingItem(null, "Bonus",25, true))
                bookingDao.insertBooking(BookingItem(null, "Book",55, false))
            }
            bookingItemList.addAll(bookingDao.getAllBooking())

            runOnUiThread {
                bookingAdapter = BookingAdapter(bookingItemList, this@MainActivity)

                mainBinding.recyclerBookingView.layoutManager = LinearLayoutManager(this@MainActivity)
                mainBinding.recyclerBookingView.setHasFixedSize(true)
                mainBinding.recyclerBookingView.adapter = bookingAdapter

                mainBinding.fab.setOnClickListener {
                    launchSumItemActivity()
                }
            }
        }.start()
    }

    private fun launchSumItemActivity() {
        TODO("Summary screen")
    }
}