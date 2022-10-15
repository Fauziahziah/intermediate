package com.example.mysubmission_intermediate.UI.Story.AddStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysubmission_intermediate.Api.FileUploadResponse
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.Remote.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val pref: Repository) : ViewModel() {
    val fileUploadResponse: LiveData<FileUploadResponse> = pref.fileUploadResponse
    val showLoading: LiveData<Boolean> = pref.showLoading
    val toastText: LiveData<String> = pref.toastText

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            pref.uploadStory(token, file, description)
        }
    }
    fun loadState(): LiveData<UserModel> {
        return pref.loadState()
    }
}