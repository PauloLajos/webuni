package hu.webinu.shoppinglist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.webinu.shoppinglist.adapter.ShoppingAdapter
import hu.webinu.shoppinglist.additem.AddItemActivity
import hu.webinu.shoppinglist.data.ShoppingDao
import hu.webinu.shoppinglist.data.ShoppingDatabase
import hu.webinu.shoppinglist.data.ShoppingItem
import hu.webinu.shoppinglist.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var data: ShoppingDao

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
        } else if (!intent.hasExtra("name")) {
            //Activity hasn't returned extra data
            return@registerForActivityResult
        }
        // Valid result returned
        // Add shopping item
        //itemList.add(ShoppingItem(
        Thread {
            val shoppingItem = ShoppingItem(
                    null,
                    intent.getStringExtra("category").toString().toInt(),
                    intent.getStringExtra("name").toString(),
                    intent.getStringExtra("description").toString(),
                    intent.getStringExtra("estimatedPrice").toString().toFloat(),
                    intent.getStringExtra("boughtStatus").toString().toBoolean()
                )
            // Update list
            data.insertItems(shoppingItem)
            itemList.add(shoppingItem)
            shoppingAdapter.notifyItemInserted(itemList.lastIndex)
        }.start()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        itemList = ArrayList()
        shoppingAdapter = ShoppingAdapter(itemList, this@MainActivity)
        // sample data load
        //shoppingListItems()

        //
        mainBinding.recyclerShoppingView.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerShoppingView.setHasFixedSize(true)
        mainBinding.recyclerShoppingView.adapter = shoppingAdapter

        data = ShoppingDatabase.getInstance(this).shoppingDao()
        Thread {
            if (data.getAllExamples().isEmpty()) {
                data.insertItems(ShoppingItem(null, 0, "Bred", "White", 80.0F, false))
                data.insertItems(ShoppingItem(null, 1, "Citron", "Lime", 60.0F, true))
                data.insertItems(ShoppingItem(null, 1, "Banana", "Yellow", 30.0F, false))
                data.insertItems(ShoppingItem(null, 0, "Milk", "1.5 %", 15.0F, true))
                data.insertItems(ShoppingItem(null, 2, "Bulb", "25 watts", 40.0F, false))
            }
            itemList.addAll(data.getAllExamples())
            shoppingAdapter.notifyDataSetChanged()
        }.start()

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
/*
    private fun shoppingListItems(){
        itemList.add(ShoppingItem(0,"Bred","White",80.0F,false))
        itemList.add(ShoppingItem(1,"Citron","Lime",60.0F,true))
        itemList.add(ShoppingItem(1,"Banana","Yellow",30.0F,false))
        itemList.add(ShoppingItem(0,"Milk","1.5 %",15.0F,true))
        itemList.add(ShoppingItem(2,"Bulb","25 watts",40.0F,false))
    }
*/
/*
        fun showEditDialog(shoppingItem: ShoppingItem, adapterPosition: Int) {
            // show edit dialog
            val editDialog = ShoppingItemDialog()
            val bundle = Bundle()
            bundle.putSerializable(KEY_EDIT, shoppingItem)
            editDialog.arguments = bundle

            editDialog.show(supportFragmentManager, "EDITDIALOG")
        }
*/
    companion object {
        const val KEY_EDIT = "KEY_EDIT"
    }
}