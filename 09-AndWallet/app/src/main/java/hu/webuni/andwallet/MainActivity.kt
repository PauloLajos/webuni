package hu.webuni.andwallet

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webuni.andwallet.adapter.BookingAdapter
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.data.BookingDao
import hu.webuni.andwallet.data.BookingItem
import hu.webuni.andwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var bookingDao: BookingDao
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

                mainBinding.btSave.setOnClickListener {
                    launchAddItemActivity()
                }
            }
        }.start()
    }

    private fun launchAddItemActivity() {
        Thread {
            val bookingItem = BookingItem(null,
                mainBinding.etName.text?.toString() ?: "" ,
                mainBinding.etAmount.text.toString().toIntOrNull() ?: 0,
                mainBinding.tbInOrOut.isChecked
            )

            if (bookingItem.name != "" && bookingItem.amount != 0) {

                bookingDao.insertBooking(bookingItem)

                runOnUiThread {
                    // Update list
                    bookingItemList.add(bookingItem)
                    bookingAdapter.notifyItemInserted(bookingItemList.lastIndex)

                    mainBinding.etName.text.clear()
                    mainBinding.etAmount.text.clear()
                    mainBinding.tbInOrOut.isChecked = false

                    window.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    )
                }
            }
            else {
                runOnUiThread {
                    Toast.makeText(this,"The Name or Amount field cannot be empty", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}