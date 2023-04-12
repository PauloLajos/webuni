package com.androidknowledge.example.bmicalculatordemo

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidknowledge.example.bmicalculatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set strCurrentHeight to starting value
        val res: Resources = resources
        binding.tvCurrentHeight.text = String.format(res.getString(R.string.strCurrentHeight), 170)
    }
}