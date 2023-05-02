package hu.webinu.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.webinu.shoppinglist.MainActivity
import hu.webinu.shoppinglist.R
import hu.webinu.shoppinglist.data.ShoppingDatabase
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ShoppingItemBinding

class ShoppingAdapter(private var shoppingItemList: ArrayList<ShoppingItem>, private val context: Context) :
    RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder>() {

    private val data = ShoppingDatabase.getInstance(context).shoppingDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val shoppingItemBinding = ShoppingItemBinding.inflate(
            LayoutInflater.from(context),parent,false
        )

        return ItemViewHolder(shoppingItemBinding)
    }

    override fun getItemCount() = shoppingItemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(shoppingItemList[holder.adapterPosition])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateShoppingItem(shoppingItem: ShoppingItem, position: Int) {
        Thread {
            data.updateItem(shoppingItem)
            (context as MainActivity).runOnUiThread {
                shoppingItemList[position] = shoppingItem
                notifyDataSetChanged()
            }
        }.start()
    }

    private fun deleteShoppingItem(position: Int) {
        Thread {
            data.deleteItem(shoppingItemList[position])
            (context as MainActivity).runOnUiThread {
                shoppingItemList.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    inner class ItemViewHolder(private val itemBinding: ShoppingItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            itemBinding.tvName.text = shoppingItem.name
            itemBinding.tvEstimatedPrice.text = shoppingItem.estimatedPrice.toString()

            val myItem: Array<String> =  context.resources.getStringArray(R.array.types_array)
            itemBinding.tvCategory.text = myItem[shoppingItem.category]

            itemBinding.btnDelete.setOnClickListener {
                deleteShoppingItem(adapterPosition)
            }

            itemBinding.btnEdit.setOnClickListener {
                (context as MainActivity).launchModItemActivity(shoppingItem)
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
            }

            itemBinding.cbBoughtStatus.setOnCheckedChangeListener(null)
            itemBinding.cbBoughtStatus.isChecked = shoppingItem.boughtStatus
            itemBinding.cbBoughtStatus.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
                shoppingItem.boughtStatus = checked
                updateShoppingItem(shoppingItem, adapterPosition)
            }
        }
    }
}