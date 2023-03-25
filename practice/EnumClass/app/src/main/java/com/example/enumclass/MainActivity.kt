package com.example.enumclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.enumclass.databinding.ActivityMainBinding

enum class PaymentOption {
    CASH,
    CARD,
    TRANSFER
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var paymentOption: PaymentOption = PaymentOption.CARD

    fun fnMain() {
        var str: String = ""

        val option: PaymentOption =
            PaymentOption.valueOf("TRANSFER")
        str = "${option.toString()}\n"

        str += "All options: "
        val paymentOptions: Array<PaymentOption> =
            PaymentOption.values()
        for (paymentOption in paymentOptions) {
            str += "${paymentOption.toString()}, "
        }

        binding.tvDisplay.text = str
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            //fnMain()

            // Rotate text from enum value
            var tvStr: String = ""
            var btStr: String = ""
            when (paymentOption) {
                PaymentOption.CARD -> { tvStr = "Card"; btStr = "Cash"; paymentOption = PaymentOption.CASH }
                PaymentOption.CASH -> { tvStr = "Cash"; btStr = "Transfer"; paymentOption = PaymentOption.TRANSFER }
                PaymentOption.TRANSFER -> { tvStr = "Transfer";  btStr = "Card"; paymentOption = PaymentOption.CARD }
            }
            binding.tvDisplay.text = tvStr
            binding.button.text = btStr
        }
    }
}
