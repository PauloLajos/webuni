package com.android.developer.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.android.developer.animationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAnim.setOnClickListener {
            val demoAnim = AnimationUtils.loadAnimation( this@MainActivity, R.anim.demo_anim)
            binding.btnAnim.startAnimation(demoAnim)
        }
    }
}