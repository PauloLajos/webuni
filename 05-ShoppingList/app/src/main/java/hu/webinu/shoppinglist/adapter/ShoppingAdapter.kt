package hu.webinu.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.webinu.shoppinglist.R
import hu.webinu.shoppinglist.data.ShoppingItem

class ShoppingAdapter(private val shoppingItemList : ArrayList<ShoppingItem>):
    RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // This is where we now collect our values from XML
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvEstimatedPrice)
        val cbBought: CheckBox = itemView.findViewById(R.id.cbBoughtStatus)
        val ivItemLogo: ImageView = itemView.findViewById(R.id.ivItemLogo)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.shopping_item, parent, false)
        return ItemViewHolder(viewLayout)
    }

    override fun getItemCount()= shoppingItemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = shoppingItemList[position]
        holder.tvName.text = currentItem.name
        holder.tvPrice.text = currentItem.estimatedPrice.toString()
        holder.cbBought.isChecked = currentItem.boughtStatus

        holder.btnDelete.setOnClickListener {
            deleteShoppingItem(holder.adapterPosition)
        }

    }

    fun addShoppingItem(shoppingItem: ShoppingItem){
        shoppingItemList.add(shoppingItem)
        notifyItemInserted(shoppingItemList.lastIndex)
    }

    private fun deleteShoppingItem(position: Int) {
        shoppingItemList.removeAt(position)
        notifyItemRemoved(position)
    }
}