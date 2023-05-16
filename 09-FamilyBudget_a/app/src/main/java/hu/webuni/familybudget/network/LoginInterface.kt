package hu.webuni.familybudget.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST


interface LoginInterface {
    @POST("simplelogin.php")

    fun getUserLogin(
        @Field("username") uname: String?,
        @Field("password") password: String?
    ): Call<String?>?
}