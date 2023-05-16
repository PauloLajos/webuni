package hu.webuni.andwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import hu.webuni.andwallet.adapter.BookingAdapter
import hu.webuni.andwallet.data.BookingData
import hu.webuni.andwallet.databinding.ActivityMainBinding
import hu.webuni.andwallet.touch.BookingRecyclerTouchCallback
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val PREF_SETTINGS = "PREF_SETTINGS"
        const val KEY_LAST_OPENED = "KEY_LAST_OPENED"
        const val KEY_FIRST_START = "KEY_FIRST_START"
    }

    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bookingAdapter = BookingAdapter(this@MainActivity)
        binding.recyclerView.adapter = bookingAdapter

        val touchCallback = BookingRecyclerTouchCallback(bookingAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        saveData()
    }

    private fun saveData() {
        val sp = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(KEY_LAST_OPENED, Date(Calendar.getInstance().timeInMillis).toString())
        editor.putBoolean(KEY_FIRST_START,false)
        editor.apply()
    }
}