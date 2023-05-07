package hu.webuni.moneyexchangeshttpdemo.network

import hu.webuni.moneyexchangeshttpdemo.data.MoneyResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// https://api.apilayer.com/exchangerates_data/
// apikey=A5JThgHh99On95JAyFVrUcdkhvshbk81
// convert
// ?to=HUF&from=EUR&amount=1
interface MoneyAPI {
    @Headers(
        "apikey: A5JThgHh99On95JAyFVrUcdkhvshbk81"
    )
    @GET(
        "convert"
    )

    fun getMoney(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ) : Call<MoneyResult>
}