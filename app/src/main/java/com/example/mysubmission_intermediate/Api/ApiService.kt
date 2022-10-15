package com.example.mysubmission_intermediate.Api

import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("v1/stories")
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

    @Multipart
    @POST("v1/stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<FileUploadResponse>

}