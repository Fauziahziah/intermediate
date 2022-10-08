package com.example.mysubmission_intermediate.Api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("v1/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("v1/login")
    fun userLogin (
        @Field("email") name: String,
        @Field("password") email: String
    ): Call<LoginResponses.LoginResponse>

}