package hu.webinu.shoppinglist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.webinu.shoppinglist.data.ShoppingItem

class ShoppingItemDialog: DialogFragment() {

    interface ShoppingItemDialogHandler {
        fun shoppingItemCreated(item: ShoppingItem)
        fun shoppingItemUpdated(item: ShoppingItem)
    }

    private lateinit var shoppingItemHandler: ShoppingItemDialogHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShoppingItemDialogHandler) {
            shoppingItemHandler = context
        } else {
            throw RuntimeException("The Activity does not implement the ShoppingItemDialogHandler interface")
        }
    }

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var spinnerCategory: Spinner

    private var EDIT_MODE = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        if (this.arguments!= null && this.requireArguments().containsKey(MainActivity.KEY_EDIT)) {
            EDIT_MODE = true
        }

        if (EDIT_MODE) {
            builder.setTitle("Edit Item")
        } else {
            builder.setTitle("New Item")
        }

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.activity_add_item, null
        )

        etName = rootView.findViewById(R.id.etItemName)
        etPrice = rootView.findViewById(R.id.etEstimatedPrice)
        spinnerCategory = rootView.findViewById(R.id.itemType)
        var categoryAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.types_array,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter

        builder.setView(rootView)

        if (EDIT_MODE) {
            var shopItem = this.requireArguments().getSerializable(MainActivity.KEY_EDIT) as ShoppingItem

            etName.setText(shopItem.name)
            etPrice.setText(shopItem.estimatedPrice.toString())
            spinnerCategory.setSelection(shopItem.category)
        }

        builder.setPositiveButton("OK") { dialog, which ->
            //... keep empty
        }
        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (etName.text.isNotEmpty()) {
                if (etPrice.text.isNotEmpty()) {
                    if (EDIT_MODE) {
                        handleItemUpdate()
                    } else {
                        handleItemCreate()
                    }

                    dialog.dismiss()
                } else {
                    etPrice.error = "This field can not be empty"
                }
            } else {
                etName.error = "This field can not be empty"
            }
        }
    }

    fun handleItemCreate() {
        shoppingItemHandler.shoppingItemCreated(
            ShoppingItem(
                0,
                etName.text.toString(),
                etPrice.text.toString(),
                spinnerCategory.selectedItemPosition.toString().toFloat(),
                false
            )
        )
    }

    private fun handleItemUpdate() {
        var shopItemToEdit = this.requireArguments().getSerializable(MainActivity.KEY_EDIT) as ShoppingItem

        shopItemToEdit.name = etName.text.toString()
        shopItemToEdit.estimatedPrice = etPrice.text.toString().toFloat()
        shopItemToEdit.category = spinnerCategory.selectedItemPosition

        shoppingItemHandler.shoppingItemUpdated(shopItemToEdit)
    }
}