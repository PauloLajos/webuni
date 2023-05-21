package hu.webuni.andwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.webuni.andwallet.MainActivity
import hu.webuni.andwallet.R
import hu.webuni.andwallet.data.AppDatabase
import hu.webuni.andwallet.data.BookingItem
import hu.webuni.andwallet.databinding.BookingItemBinding

class BookingAdapter(
    private var bookingItemList: ArrayList<BookingItem>,
    private val context: Context,
    private var onItemDeleteClickListener: OnItemDeleteClickListener
) : RecyclerView.Adapter<BookingAdapter.ItemViewHolder>() {

    private val bookingDao = AppDatabase.getInstance(context).bookingDao()

    inner class ItemViewHolder(private val itemBinding: BookingItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(bookingItem: BookingItem) {
            itemBinding.tvName.text = bookingItem.name
            itemBinding.tvAmount.text = bookingItem.amount.toString()
            if (bookingItem.income)
                itemBinding.imageView.setImageResource(R.drawable.income)
            else
                itemBinding.imageView.setImageResource(R.drawable.expenditure)

            itemBinding.btnDelete.setOnClickListener {
                deleteBookingItem(adapterPosition)
            }
        }
    }

    interface OnItemDeleteClickListener {
        fun onItemDeleteClick(bookingBalance: Int)
    }

    private fun deleteBookingItem(position: Int) {
        Thread {
            bookingDao.deleteBooking(bookingItemList[position])

            val bookingBalance = bookingDao.getSumBalance()

            (context as MainActivity).runOnUiThread {
                bookingItemList.removeAt(position)
                notifyItemRemoved(position)

                onItemDeleteClickListener.onItemDeleteClick(bookingBalance)
            }
        }.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val bookingItemBinding = BookingItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ItemViewHolder(bookingItemBinding)
    }

    override fun getItemCount(): Int {
        return bookingItemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(bookingItemList[holder.adapterPosition])
    }
}