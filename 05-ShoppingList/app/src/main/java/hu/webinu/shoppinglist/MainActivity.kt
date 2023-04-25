package hu.webinu.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webinu.shoppinglist.adapter.ShoppingAdapter
import hu.webinu.shoppinglist.additem.AddItemActivity
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

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddItemActivity::class.java)
            startActivity(intent)
        }

        // create the get Intent object
        val intent = intent
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        val str = intent.getStringExtra("itemName")
        // display the string
        if (str?.isEmpty() == false) {
            itemList.add(
                ShoppingItem(
                    //intent.getStringExtra("itemType").toString(),
                    "Food",
                    str,
                    intent.getStringExtra("itemDescription").toString(),
                    intent.getStringExtra("itemPrice")!!.toFloat(),
                    intent.getStringExtra("itemBought").toString().toBoolean()
                )
            )

            Toast.makeText((this@MainActivity), str, Toast.LENGTH_SHORT).show()
        }
    }

    private fun shoppingListItems(){
        itemList.add(ShoppingItem("Food", "Bred", "White", 80.0F, false))
        itemList.add(ShoppingItem("Fruit", "Citron", "Lime", 60.0F, true))
        itemList.add(ShoppingItem("Fruit", "Banana", "Yellow", 30.0F, false))
        itemList.add(ShoppingItem("Food", "Milk", "1.5 %", 15.0F, true))
        itemList.add(ShoppingItem("Electric", "Bulb", "25 watts", 40.0F, false))
    }
}