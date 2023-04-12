package com.example.loginlayoutdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
//import com.example.loginlayoutdemo.databinding.ActivityMainBinding
import com.example.loginlayoutdemo.databinding.ActivityMainTextinputlayoutBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var binding: ActivityMainBinding
    private lateinit var binding: ActivityMainTextinputlayoutBinding

    fun toastUserName(buttonStr: String, view: View?) {
        // Hide the keyboard.
        val imm = getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        // Toast button- and username
        val userName = if (binding.etUserName.text?.isEmpty() == true) "Nobody" else binding.etUserName.text.toString()
        val passWord = if (binding.etPassword.text?.isEmpty() == true) "empty" else binding.etPassword.text.toString()
        Toast.makeText(this@MainActivity, "$buttonStr: $userName \nPassword: $passWord", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainTextinputlayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Use setOnClickListener() Without a Callback in Kotlin
        binding.btLogin.setOnClickListener {
            toastUserName("Login", it)
        }

        // Use setOnClickListener() With a Callback in Kotlin
        binding.btRegister.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                toastUserName("Register", view)
            }
        })

        // Use setOnClickListener() With a Lambda Expression in Kotlin
        binding.btAbout.setOnClickListener { view -> toastUserName("About", view) }

        // Use the View.OnClickListener Interface on the MainActivity Class in Kotlin
        binding.btHelp.setOnClickListener(this) // Applying onCLick Listener to the button
    }

    // Use the View.OnClickListener Interface on the MainActivity Class in Kotlin
    //   class MainActivity : AppCompatActivity(), View.OnClickListener {}
    //   binding.btHelp.setOnClickListener(this)
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btHelp -> {
                toastUserName("Help", view)
            }
        }
    }
}