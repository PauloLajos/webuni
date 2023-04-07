package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.databinding.ActivityMainBinding

/*
    Külső könyvtárak:
    https://facebook.github.io/shimmer-android/
    https://github.com/wasabeef/awesome-android-ui
*/
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        binding.btnReset.setOnClickListener {
            binding.ticView.resetGame()
        }
    }

    public fun showText(text: String) {
        binding.tvData.text = text
    }
}