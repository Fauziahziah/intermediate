package com.example.mysubmission_intermediate.Remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mysubmission_intermediate.Api.*
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.Model.UserPreference
import com.example.mysubmission_intermediate.Remote.Data.StoryPagingSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    private val _StroriesResponse = MutableLiveData<StroriesResponse>()
    val StoriesResponse: LiveData<StroriesResponse> = _StroriesResponse

    private val _fileuploadResponse = MutableLiveData<FileUploadResponse>()
    val fileUploadResponse: LiveData<FileUploadResponse> = _fileuploadResponse

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(preferences, apiService)
            }.also { instance = it }
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

    fun getAllStories(token: String): LiveData<PagingData<StoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        _showLoading.value = true
        try {
            val client = apiService.uploadImage(token, file, description)
            client.enqueue(object : Callback<FileUploadResponse> {
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    _showLoading.value = false
                    if (response.isSuccessful && response.body() != null) {

                        _fileuploadResponse.value = response.body()
                        _toastText.value = response.body()?.message
                    } else {
                        _toastText.value = response.message().toString()
                        Log.e(
                            TAG,
                            "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                        )
                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    Log.d("error upload", t.message.toString())
                }

            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun loadState(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUser(session: UserModel) {
        pref.saveUSer(session)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }





}