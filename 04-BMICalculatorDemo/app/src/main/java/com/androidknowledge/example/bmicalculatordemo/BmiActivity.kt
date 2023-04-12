package com.androidknowledge.example.bmicalculatordemo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidknowledge.example.bmicalculatordemo.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    private var gender: String = ""
    private var height: String = ""
    private var weight: String = ""
    private var age:    String = ""
    private var intBmi: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bmi)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        gender = intent.getStringExtra("gender").toString()
        height = intent.getStringExtra("height").toString()
        weight = intent.getStringExtra("weight").toString()
        age = intent.getStringExtra("age").toString()

        binding.genderDisplay.text = gender

        intBmi = weight.toDouble() / ( (height.toDouble() / 100) * (height.toDouble() / 100) )
        binding.tvBmiDisplay.text = String.format("%.3f", intBmi)

        val bmiCategory: String =
            when (intBmi) {
                in 00.0..16.0 -> {
                    binding.ivResult.setImageResource(R.drawable.crosss)
                    binding.clResult.setBackgroundColor(Color.RED)
                    "Severe Thinness" }
                in 16.0..17.0 -> {
                    binding.ivResult.setImageResource(R.drawable.warning)
                    "Moderate Thinness"
                }
                in 17.0..18.5 -> {
                    binding.ivResult.setImageResource(R.drawable.warning)
                    "Mild Thinness"
                }
                in 18.5..25.0 -> {
                    binding.ivResult.setImageResource(R.drawable.ok)
                    "Normal"
                }
                in 25.0..30.0 -> {
                    binding.ivResult.setImageResource(R.drawable.warning)
                    "Overweight"
                }
                in 30.0..35.0 -> {
                    binding.ivResult.setImageResource(R.drawable.crosss)
                    binding.clResult.setBackgroundColor(Color.RED)
                    "Obese Class I"
                }
                in 35.0..40.0 -> {
                    binding.ivResult.setImageResource(R.drawable.crosss)
                    binding.clResult.setBackgroundColor(Color.RED)
                    "Obese Class II"
                }
                else -> {
                    binding.ivResult.setImageResource(R.drawable.crosss)
                    binding.clResult.setBackgroundColor(Color.RED)
                    "Obese Class III"
                }
            }
        binding.tvBmiCategory.text = bmiCategory

        binding.btReCalculateBmi.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}