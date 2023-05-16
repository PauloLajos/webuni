package hu.webuni.familybudget.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST


interface RegisterInterface {
    @POST("simpleregister.php")

    fun getUserRegi(
        @Field("name") name: String?,
        @Field("username") uname: String?,
        @Field("password") password: String?
    ): Call<String?>?
}