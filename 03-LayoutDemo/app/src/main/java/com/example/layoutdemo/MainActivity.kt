package com.example.layoutdemo

import android.graphics.Color
import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.example.layoutdemo.databinding.ActivityMainBinding
import com.example.layoutdemo.databinding.ActivityViewDemoBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewDemoBinding
    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_demo)


        //binding = ActivityMainBinding.inflate(layoutInflater)
        binding = ActivityViewDemoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val spannable = SpannableString("Text is spantastic!")
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            8, 12,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            StyleSpan(BOLD),
            8, spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView.text = spannable
    }
}