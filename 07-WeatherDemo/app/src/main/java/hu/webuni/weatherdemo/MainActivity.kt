package hu.webuni.weatherdemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.weatherdemo.data.WeatherResult
import hu.webuni.weatherdemo.databinding.ActivityMainBinding
import hu.webuni.weatherdemo.network.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        val retrofit = Retrofit.Builder()
            // baseUrl must end in /
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherAPI = retrofit.create(WeatherAPI::class.java)

        mainBinding.btnGetRates.setOnClickListener {
            val weatherCall = weatherAPI.getWeather()
            weatherCall.enqueue(object : Callback<WeatherResult> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                    mainBinding.tvWeatherMain.text = "Békéscsaba,hu: ${response.body()?.weather?.get(0)?.main.toString()}"
                    mainBinding.tvWeatherTemp.text = "Temperature = ${response.body()?.main?.temp.toString()} C°"
                    mainBinding.tvWeatherPressure.text = "Pressure = ${response.body()?.main?.pressure.toString()} hPa"
                    mainBinding.tvWeatherHumidity.text = "Humidity = ${response.body()?.main?.humidity.toString()}%"
                }

                override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                    mainBinding.tvWeatherTemp.text = t.message
                }
            })
        }
    }
}