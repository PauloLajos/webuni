package com.example.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.viewbinding.databinding.ActivityMainBinding
import java.util.Date
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var rand = Random()

        binding.btnTime.setOnClickListener{
            var num = rand.nextInt(100)
            var currentTime = "$num ${binding.etData.text.toString()} ${Date(System.currentTimeMillis()).toString()}"

            if (binding.etData.text.isNotEmpty()) {
                Toast.makeText(this, currentTime, Toast.LENGTH_LONG).show()
                binding.tvHello.text = currentTime
            } else {
                binding.etData.setError("Ez nem lehet üres!")
            }
        }

        binding.btnExit.setOnClickListener {
                finish()
                System.exit(0)
            }
        }
    }
