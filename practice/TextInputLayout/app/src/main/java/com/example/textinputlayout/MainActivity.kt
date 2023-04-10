package com.example.textinputlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.textinputlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.etUserName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        fun toastUserName(s: String) {
            val userName = binding.etUserName.text.toString()
            Toast.makeText(this@MainActivity, "$s: $userName", Toast.LENGTH_SHORT).show()
        }
        binding.btLogin.setOnClickListener {
            toastUserName("Login")
        }

        binding.btRegister.setOnClickListener {
            toastUserName("Register")
        }
    }
}