package br.com.ibarra.docibarra.data.remote

import br.com.ibarra.docibarra.data.model.AuthUser
import retrofit2.Call
import retrofit2.http.*

interface LoginApi {
    @FormUrlEncoded
    @Headers(
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded",
            "Authorization: Basic aXBob25lOmlwaG9uZXdpbGxub3RiZXRoZXJlYW55bW9yZQ=="
    )
    @POST("/oauth/token?grant_type=password")
    fun login(@Field("username") username: String,@Field("password") password: String): Call<AuthUser>
}