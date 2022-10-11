package com.example.mysubmission_intermediate.Api

import android.location.Location
import retrofit2.Call
import retrofit2.http.*


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

    @GET("V1/stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): StroriesResponse

    @GET("v1/stories")
    fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") includeLocation: Int = 1
    ): Call<StroriesResponse>

}