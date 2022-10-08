package com.example.mysubmission_intermediate.Remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData

import com.example.mysubmission_intermediate.Api.ApiService
import com.example.mysubmission_intermediate.Api.LoginResponses
import com.example.mysubmission_intermediate.Api.RegisterResponse
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.Model.UserPreference




class Repository private constructor(
    private val pref: UserPreference,
    private val apiService: ApiService

) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponses.LoginResponse>()
    val loginResponse: LiveData<LoginResponses.LoginResponse> = _loginResponse

    private val _loginResult = MutableLiveData<LoginResponses.LoginResult?>()
    val loginResult: MutableLiveData<LoginResponses.LoginResult?> = _loginResult

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            pref: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(pref, apiService)
            }
    }

    fun registerAccount(name: String, email: String, password: String) {
        _showLoading.value = true
        val client = apiService.userRegister(name, email, password)

        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful && response.body() !=null) {
                    _registerResponse.value = response.body()
                    _toastText.value = response.body()?.message
                } else {
                    _toastText.value = response.message().toString()
                    Log.e(TAG, "onFailure: ${response.message()}, ${response.body()?.message.toString()}" )

                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toastText.value = t.message.toString()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun loginAccount(email: String, password: String) {
        _showLoading.value = true
        val client = apiService.userLogin(email, password)

        client.enqueue(object: Callback<LoginResponses.LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponses.LoginResponse>,
                response: Response<LoginResponses.LoginResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = response.body()?.message
                    _loginResult.value = response.body()?.loginResult
                } else {
                    _toastText.value = response.message().toString()
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponses.LoginResponse>, t: Throwable) {
                _toastText.value = t.message.toString()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun setupToken(token: String): String = "Bearer $token"

    suspend fun saveUser(session: UserModel) {
        pref.saveUSer(session)
    }

    suspend fun login() {
        pref.login()
    }





}