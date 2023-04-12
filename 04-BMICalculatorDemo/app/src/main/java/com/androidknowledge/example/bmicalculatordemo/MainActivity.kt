package com.androidknowledge.example.bmicalculatordemo

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidknowledge.example.bmicalculatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var intWeight: Int = 55
    var intAge: Int = 35
    var currentProgress: Int = 170
    var typeOfUser: String = "0"
    //var mintProgress: String = currentProgress.toString()
    var weight: String = intWeight.toString()
    var age: String = intAge.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set strCurrentHeight to starting value
        val res: Resources = resources
        binding.tvCurrentHeight.text = String.format(res.getString(R.string.strCurrentHeight), currentProgress)

        binding.seekBarForHeight.max = 240
        binding.seekBarForHeight.progress = currentProgress
        binding.seekBarForHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // here, you react to the value being set in seekBar
                currentProgress = progress
                binding.tvCurrentHeight.text = String.format(res.getString(R.string.strCurrentHeight), currentProgress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })

        binding.tvCurrentWeight.text = weight

        binding.tvCurrentAge.text = age

        // Male gender
        binding.clMale.setOnClickListener {
            binding.clMale.setBackgroundResource(R.drawable.focuscardbackground)
            binding.clFemale.setBackgroundResource(R.drawable.notfocuscardbackground)
            typeOfUser = "Male"
        }

        // Female gender
        binding.clFemale.setOnClickListener {
            binding.clMale.setBackgroundResource(R.drawable.notfocuscardbackground)
            binding.clFemale.setBackgroundResource(R.drawable.focuscardbackground)
            typeOfUser = "Female"
        }

        // Set weight
        binding.incrementWeight.setOnClickListener {
            if (intWeight < 200) intWeight++
            binding.tvCurrentWeight.text = intWeight.toString()
        }
        binding.decrementWeight.setOnClickListener {
            if (intWeight > 0) intWeight--
            binding.tvCurrentWeight.text = intWeight.toString()
        }

        // Set age
        binding.incrementAge.setOnClickListener {
            if (intAge < 120) intAge++
            binding.tvCurrentAge.text = intAge.toString()
        }
        binding.decrementAge.setOnClickListener {
            if (intAge > 0) intAge--
            binding.tvCurrentAge.text = intAge.toString()
        }

        // Calculate BMI
        binding.btCalculateBmi.setOnClickListener {
            if (typeOfUser != "0") {
                val intent = Intent(this@MainActivity, BmiActivity::class.java)

                intent.putExtra("gender", typeOfUser)
                intent.putExtra("height", currentProgress.toString())
                intent.putExtra("weight", intWeight.toString())
                intent.putExtra("age", intAge.toString())

                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, getString(R.string.strSelectGenderFirst), Toast.LENGTH_SHORT).show()
            }
        }

    }
}