package hu.webuni.moneyexchangeshttpdemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.moneyexchangeshttpdemo.data.MoneyResult
import hu.webuni.moneyexchangeshttpdemo.databinding.ActivityMainBinding
import hu.webuni.moneyexchangeshttpdemo.network.MoneyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        val retrofit = Retrofit.Builder()
                // baseUrl must end in /
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val moneyAPI = retrofit.create(MoneyAPI::class.java)

        mainBinding.btnGetRates.setOnClickListener {
            val moneyCall = moneyAPI.getMoney("HUF", "EUR", "1")
            moneyCall.enqueue(object : Callback<MoneyResult> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<MoneyResult>, response: Response<MoneyResult>) {
                    mainBinding.tvData.text = "1 EUR = ${response.body()?.result.toString()} HUF"
                }

                override fun onFailure(call: Call<MoneyResult>, t: Throwable) {
                    mainBinding.tvData.text = t.message
                }
            })
        }
    }
}