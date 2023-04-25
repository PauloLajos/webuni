package hu.webinu.shoppinglist.additem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.webinu.shoppinglist.MainActivity
import hu.webinu.shoppinglist.R
import hu.webinu.shoppinglist.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var spinner: Spinner
    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_item)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spinner = findViewById<Spinner>(R.id.itemType)
        spinner.onItemSelectedListener = this

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.types_array,
            R.layout.spinner_list
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_list)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        binding.btnAddItem.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)

            intent.putExtra("itemType", binding.itemType.toString())
            intent.putExtra("itemName", binding.etItemName.text.toString())
            intent.putExtra("itemDescription", binding.etItemDescription.text.toString())
            intent.putExtra("itemPrice", binding.etEstimatedPrice.text.toString())
            intent.putExtra("itemBought", binding.checkBox.isChecked.toString())

            startActivity(intent)
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // On selecting a spinner item
        val item = parent.getItemAtPosition(position).toString()

        // Showing selected spinner item
        //Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Another interface callback
    }
}