package hu.webinu.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.webinu.shoppinglist.MainActivity
import hu.webinu.shoppinglist.R
import hu.webinu.shoppinglist.data.ShoppingDatabase
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ShoppingItemBinding


class ShoppingAdapter(var shoppingItemList: ArrayList<ShoppingItem>, private val context: Context) :
    RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val shoppingItemBinding = ShoppingItemBinding.inflate(
            LayoutInflater.from(context),parent,false
        )

        //val viewLayout = LayoutInflater.from(parent.context).inflate(
        //    R.layout.shopping_item, parent, false)
        return ItemViewHolder(shoppingItemBinding)
    }

    override fun getItemCount()= shoppingItemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //val currentItem = shoppingItemList[position]
        holder.bind(shoppingItemList[holder.adapterPosition])
    }

    private fun showDialog() {
    }

    fun addShoppingItem(shoppingItem: ShoppingItem){
        shoppingItemList.add(shoppingItem)
        notifyItemInserted(shoppingItemList.lastIndex)
    }

    fun updateItem(item: ShoppingItem, editIndex: Int) {
        shoppingItemList[editIndex] = item
        notifyItemChanged(editIndex)
    }

    private fun deleteShoppingItem(position: Int) {
        var data = ShoppingDatabase.getInstance(context).shoppingDao()
        Thread {
            data.deleteItem(shoppingItemList[position])
        }.start()
        shoppingItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ItemViewHolder(private val itemBinding: ShoppingItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            itemBinding.tvName.text = shoppingItem.name
            itemBinding.tvEstimatedPrice.text = shoppingItem.estimatedPrice.toString()


            val myItem: Array<String> =  context.resources.getStringArray(R.array.types_array)
            itemBinding.tvCategory.text = myItem[shoppingItem.category!!]

            itemBinding.cbBoughtStatus.isChecked = shoppingItem.boughtStatus

            itemBinding.btnDelete.setOnClickListener {
                deleteShoppingItem(adapterPosition)
            }
            itemBinding.btnEdit.setOnClickListener {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                //(context as MainActivity).showEditDialog(
                //    shoppingItemList[adapterPosition], adapterPosition
                //)
            }
        }
    }
}