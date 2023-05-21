package hu.webuni.andwallet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webuni.andwallet.adapter.BookingAdapter
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.data.BookingDao
import hu.webuni.andwallet.data.BookingItem
import hu.webuni.andwallet.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var bookingDao: BookingDao
    private lateinit var bookingItemList: ArrayList<BookingItem>
    private lateinit var bookingAdapter: BookingAdapter

    companion object {
        const val PREF_SETTINGS = "PREF_SETTINGS"
        const val KEY_LAST_OPENED = "KEY_LAST_OPENED"
        const val KEY_FIRST_START = "KEY_FIRST_START"
    }

    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        Thread {
            bookingDao.deleteAll()

            runOnUiThread {
                bookingItemList.clear()

                val size = bookingAdapter.itemCount
                bookingAdapter.notifyItemRangeRemoved(0, size)

                mainBinding.recyclerBookingView.removeAllViews()

                showBalanceInTextview(0)
            }
        }.start()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.btDeleteAll.setOnClickListener {
            if (bookingAdapter.itemCount>0){
                val builder = AlertDialog.Builder(this)

                with(builder) {
                    setTitle("Delete ALL items")
                    setMessage("Are you sure?")
                    setPositiveButton("OK", positiveButtonClick)
                    setNegativeButton("CANCEL", null)
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
            else
                Toast.makeText(
                    this,
                    "There is nothing to delete...",
                    Toast.LENGTH_LONG
                ).show()
        }

        mainBinding.btSum.setOnClickListener {
            val intent = Intent(this@MainActivity, SummaryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            finish()
        }

        initRecyclerView()

        savePreferenceData()
    }

    private fun savePreferenceData() {
        val sharedPreferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LAST_OPENED, Date(Calendar.getInstance().timeInMillis).toString())
        editor.putBoolean(KEY_FIRST_START,false)
        editor.apply()
    }

    private fun initRecyclerView() {
        bookingItemList = ArrayList()
        bookingDao = AppDatabase.getInstance(this).bookingDao()

        val firstStart = isAppFirstStart()
        Thread {
            //Test data
            if (bookingDao.getAllBooking().isEmpty() && firstStart) {
                bookingDao.insertBooking(BookingItem(null, "Food",100,false))
                bookingDao.insertBooking(BookingItem(null, "Salary",200,true))
                bookingDao.insertBooking(BookingItem(null, "Wear",150,false))
                bookingDao.insertBooking(BookingItem(null, "Bonus",25, true))
                bookingDao.insertBooking(BookingItem(null, "Book",55, false))
            }
            bookingItemList.addAll(bookingDao.getAllBooking())

            val bookingBalance = bookingDao.getSumBalance()

            runOnUiThread {
                bookingAdapter = BookingAdapter(
                    bookingItemList,
                    this@MainActivity,
                    object : BookingAdapter.OnItemDeleteClickListener {
                        override fun onItemDeleteClick(bookingBalance: Int) {
                            showBalanceInTextview(bookingBalance)
                        }
                    }
                )

                mainBinding.recyclerBookingView.layoutManager = LinearLayoutManager(this@MainActivity)
                mainBinding.recyclerBookingView.setHasFixedSize(true)
                mainBinding.recyclerBookingView.adapter = bookingAdapter

                showBalanceInTextview(bookingBalance)

                mainBinding.btSave.setOnClickListener {
                    launchAddItemActivity()
                }
            }
        }.start()
    }

    private fun isAppFirstStart(): Boolean {
        val sharedPreferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_FIRST_START, true)
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

                val bookingBalance = bookingDao.getSumBalance()

                runOnUiThread {
                    // Update list
                    bookingItemList.add(bookingItem)
                    bookingAdapter.notifyItemInserted(bookingItemList.lastIndex)

                    showBalanceInTextview(bookingBalance)

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

    private fun showBalanceInTextview(bookingBalance: Int) {
        mainBinding.tvBalance.text = getString(R.string.balance, bookingBalance)
    }
}