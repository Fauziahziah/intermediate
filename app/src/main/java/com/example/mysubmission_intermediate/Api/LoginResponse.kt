package com.example.mysubmission_intermediate.Api

import com.google.gson.annotations.SerializedName

class LoginResponses {
    data class LoginResponse(

        @field:SerializedName("loginResult")
        val loginResult: LoginResult,

        @field:SerializedName("error")
        val error: Boolean,

        @field:SerializedName("message")
        val message: String

    )

    data class LoginResult(

        @field:SerializedName("name")
        val name: String,

        @field: SerializedName("token")
        val token: String
    )

}