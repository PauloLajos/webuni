package hu.webinu.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webinu.shoppinglist.adapter.ShoppingAdapter
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var itemList: ArrayList<ShoppingItem>
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemList = ArrayList()
        shoppingAdapter = ShoppingAdapter(itemList)
        shoppingListItems()

        binding.recyclerShoppingView.layoutManager = LinearLayoutManager(this)
        binding.recyclerShoppingView.setHasFixedSize(true)
        binding.recyclerShoppingView.adapter = shoppingAdapter
    }

    private fun shoppingListItems(){
        itemList.add(ShoppingItem(0, "Bred", "White", 80.0F, false))
        itemList.add(ShoppingItem(0, "Citron", "Lime", 60.0F, true))
        itemList.add(ShoppingItem(0, "Banana", "Yellow", 30.0F, false))
        itemList.add(ShoppingItem(0, "Milk", "1.5 %", 15.0F, true))
        itemList.add(ShoppingItem(0, "Bulb", "25 watts", 40.0F, false))
    }
}