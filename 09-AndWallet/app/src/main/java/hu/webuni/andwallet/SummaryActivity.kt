package hu.webuni.andwallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.data.BookingDao
import hu.webuni.andwallet.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    lateinit var summaryBinding: ActivitySummaryBinding

    private lateinit var bookingDao: BookingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        summaryBinding = ActivitySummaryBinding.inflate(layoutInflater)
        val view = summaryBinding.root
        setContentView(view)

        initSummaryView()

        summaryBinding.btBack.setOnClickListener {
            val intent = Intent(this@SummaryActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            finish()
        }

    }

    private fun initSummaryView() {
        bookingDao = AppDatabase.getInstance(this).bookingDao()

        Thread {
            val bookingSumIncome = bookingDao.getSumIncome()
            val bookingSumExpense = bookingDao.getSumExpense()
            val bookingBalance = bookingDao.getSumBalance()

            runOnUiThread {
                summaryBinding.tvIncome.text = getString(R.string.sumIncome, bookingSumIncome)
                summaryBinding.tvExpense.text = getString(R.string.sumExpense, bookingSumExpense)
                summaryBinding.tvBalance.text = getString(R.string.sumBalance, bookingBalance)
            }
        }.start()
    }
}