package hu.webinu.shoppinglist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webinu.shoppinglist.adapter.ShoppingAdapter
import hu.webinu.shoppinglist.additem.AddItemActivity
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

     lateinit var itemList: ArrayList<ShoppingItem>
     lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemList = ArrayList()
        shoppingAdapter = ShoppingAdapter(itemList)
        // sample data load
        shoppingListItems()

        binding.recyclerShoppingView.layoutManager = LinearLayoutManager(this)
        binding.recyclerShoppingView.setHasFixedSize(true)
        binding.recyclerShoppingView.adapter = shoppingAdapter

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddItemActivity::class.java)
            startActivity(intent)
        }

        val bundle: Bundle? = intent.extras
        //Parcelable Data
        val sitem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("shoppingItem", ShoppingItem::class.java)
        } else {
            intent.getParcelableExtra<ShoppingItem>("shoppingItem")
        }
        //val sitem: ShoppingItem? = getParcelable("shoppingItem")
        Toast.makeText((this@MainActivity), sitem!!.name.toString(), Toast.LENGTH_SHORT).show()
        itemList.add(sitem)
    }

    private fun shoppingListItems(){
        itemList.add(ShoppingItem("Food", "Bred", "White", 80.0F, false))
        itemList.add(ShoppingItem("Fruit", "Citron", "Lime", 60.0F, true))
        itemList.add(ShoppingItem("Fruit", "Banana", "Yellow", 30.0F, false))
        itemList.add(ShoppingItem("Food", "Milk", "1.5 %", 15.0F, true))
        itemList.add(ShoppingItem("Electric", "Bulb", "25 watts", 40.0F, false))
    }
}