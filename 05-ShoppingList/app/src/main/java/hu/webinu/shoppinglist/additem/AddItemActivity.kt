package hu.webinu.shoppinglist.additem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import hu.webinu.shoppinglist.R
import hu.webinu.shoppinglist.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var mainBinding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityAddItemBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        /****
         * Spinner
         */
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        mainBinding.itemType.onItemSelectedListener = this

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.types_array,
            R.layout.spinner_list
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_list)
            // Apply the adapter to the spinner
            mainBinding.itemType.adapter = adapter
        }

        /***
         * Back send data to MainActivity
         */
        mainBinding.btnAddItem.setOnClickListener {
            val returnIntent = Intent()
            if (mainBinding.etItemName.text.toString().isNotEmpty()) {
                returnIntent.putExtra("category", mainBinding.itemType.selectedItemPosition.toString() )
                returnIntent.putExtra("name", mainBinding.etItemName.text.toString() )
                returnIntent.putExtra("description", mainBinding.etItemDescription.text.toString() )
                returnIntent.putExtra("estimatedPrice", mainBinding.etEstimatedPrice.text.toString() )
                returnIntent.putExtra("boughtStatus", mainBinding.checkBox.isChecked.toString() )
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    // Spinner selected item
    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // On selecting a spinner item
        //val item = parent.getItemAtPosition(position).toString()

        // Showing selected spinner item
        //Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    // Spinner nothing selected
    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Another interface callback
    }
}