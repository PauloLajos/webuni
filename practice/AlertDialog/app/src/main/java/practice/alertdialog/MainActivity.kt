package practice.alertdialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                    finish()

    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT
        ).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            "Maybe", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        Log.i("TAG", "handleOnBackPressed: Exit")
                        exitOnBackPressed()
                    }
                })
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun exitOnBackPressed() {

        val items = arrayOf("Red", "Orange", "Yellow", "Blue")
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("Icon and Button Color")
            setMessage("We have a message")
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            }
            setPositiveButton("OK", positiveButtonClick)
            setNegativeButton("CANCEL", negativeButtonClick)
            setNeutralButton("NEUTRAL", neutralButtonClick)
            setPositiveButtonIcon(getDrawable(android.R.drawable.ic_menu_call))
            setIcon(getDrawable(android.R.drawable.ic_dialog_alert))
        }
        val alertDialog = builder.create()
        alertDialog.show()

        val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        with(positiveButton) {
            setBackgroundColor(Color.BLACK)
            setPadding(0, 0, 20, 0)
            setTextColor(Color.WHITE)
        }
        //positiveButton.setOnClickListener{ finish() }

        /*
        val items = arrayOf("Red", "Orange", "Yellow", "Blue")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("List of Items")
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            }

            setPositiveButton("OK", positiveButtonClick)
            show()
        }
         */
    }
}