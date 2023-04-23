package com.android.developer.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.android.developer.animationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val demoAnim = AnimationUtils.loadAnimation( this@MainActivity, R.anim.demo_anim)

        demoAnim.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                Toast.makeText(this@MainActivity,
                    "Aniamtion ended", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        binding.btnAnim.setOnClickListener {
            binding.layoutMain.startAnimation(demoAnim)
            binding.btnAnim.startAnimation(demoAnim)
        }
    }
}