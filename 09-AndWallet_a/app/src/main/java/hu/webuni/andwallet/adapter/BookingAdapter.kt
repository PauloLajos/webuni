package hu.webuni.andwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.webuni.andwallet.R
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.data.BookingData
import hu.webuni.andwallet.databinding.BookingRowBinding
import hu.webuni.andwallet.touch.BookingTouchHelperCallback
import java.util.*

class BookingAdapter : RecyclerView.Adapter<BookingAdapter.ViewHolder>, BookingTouchHelperCallback {

    private val context: Context

    constructor(context: Context) : super() {
        this.context = context

        Thread {
            bookingItems = AppDatabase.getInstance(context).bookingDao().getAllBooking()
        }.start()

    }

    inner class ViewHolder(private val bookingRowBinding: BookingRowBinding) : RecyclerView.ViewHolder(bookingRowBinding.root) {

        fun bind(bookingItem: BookingData) {
            bookingRowBinding.tvName.text = bookingItem.name
            bookingRowBinding.tvAmount.text = bookingItem.amount.toString()

            if (bookingItem.income)
                bookingRowBinding.imageView.setImageResource(R.drawable.income)
            else
                bookingRowBinding.imageView.setImageResource(R.drawable.expenditure)

            bookingRowBinding.btnDelete.setOnClickListener {
                deleteBookingItem(bookingItem)
            }
        }
    }

    fun addBookingItem(bookingItem: BookingData) {
        Thread {
            AppDatabase.getInstance(context).bookingDao().insertBooking(bookingItem)

            runOnUiThread {
                notifyItemInserted(bookingItems.lastIndex)
            }
        }.start()
    }

    fun deleteBookingItem(position: Int) {
        Thread {
            AppDatabase.getInstance(context).bookingDao().deleteBooking(bookingItems[position])
        }.start()
        //bookingItems.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bookingRowBinding = BookingRowBinding.inflate(
            LayoutInflater.from(context), parent, false)

        return ViewHolder(bookingRowBinding)
    }

    override fun getItemCount(): Int {
        return bookingItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookingItems[holder.adapterPosition])
    }

    override fun onDismissed(position: Int) {
        deleteBookingItem(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(bookingItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}