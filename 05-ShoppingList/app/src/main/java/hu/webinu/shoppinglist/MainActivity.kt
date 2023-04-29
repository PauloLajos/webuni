package hu.webinu.shoppinglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webinu.shoppinglist.adapter.ShoppingAdapter
import hu.webinu.shoppinglist.additem.AddItemActivity
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var itemList: ArrayList<ShoppingItem>
    private lateinit var shoppingAdapter: ShoppingAdapter

    private var mGetNameActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Validity checks
        if (RESULT_OK != result.resultCode) {
            //Activity has returned cancelled
            return@registerForActivityResult
        }

        val intent = result.data
        if (intent == null) {
            //Activity hasn't returned an intent
            return@registerForActivityResult
        }
        else if (!intent.hasExtra("name")) {
            //Activity hasn't returned extra data
            return@registerForActivityResult
        }
        // Valid result returned
        // Add shopping item
        itemList.add(ShoppingItem(
            intent.getStringExtra("category").toString().toInt(),
            intent.getStringExtra("name").toString(),
            intent.getStringExtra("description").toString(),
            intent.getStringExtra("estimatedPrice").toString().toFloat(),
            intent.getStringExtra("boughtStatus").toString().toBoolean()
        ))
        // Update list
        shoppingAdapter.notifyItemInserted(itemList.lastIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        itemList = ArrayList()
        shoppingAdapter = ShoppingAdapter(itemList)
        // sample data load
        shoppingListItems()

        //
        mainBinding.recyclerShoppingView.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerShoppingView.setHasFixedSize(true)
        mainBinding.recyclerShoppingView.adapter = shoppingAdapter

        mainBinding.fab.setOnClickListener {
            launchAddItemActivity()
        }
    }

    // Launch AddItemActivity with intent
    private fun launchAddItemActivity() {
        // passing it the Intent you want to start
        mGetNameActivity.launch(Intent(this, AddItemActivity::class.java))
    }

    // Sample data
    private fun shoppingListItems(){
        itemList.add(ShoppingItem(0,"Bred","White",80.0F,false))
        itemList.add(ShoppingItem(1,"Citron","Lime",60.0F,true))
        itemList.add(ShoppingItem(1,"Banana","Yellow",30.0F,false))
        itemList.add(ShoppingItem(0,"Milk","1.5 %",15.0F,true))
        itemList.add(ShoppingItem(2,"Bulb","25 watts",40.0F,false))
    }
}