package hu.webuni.weatherdemo.network

import hu.webuni.weatherdemo.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://api.openweathermap.org/data/2.5/weather
 * ?q=Budapest,hu&units=metric&appid=dbbfc0182e36f26e2c37714258f1fb8d
 */
interface WeatherAPI {
    @GET(
        "weather"
    )

    fun getWeather(
        @Query("q") q: String = "Békéscsaba,hu",
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "dbbfc0182e36f26e2c37714258f1fb8d"
    ) : Call<WeatherResult>
}