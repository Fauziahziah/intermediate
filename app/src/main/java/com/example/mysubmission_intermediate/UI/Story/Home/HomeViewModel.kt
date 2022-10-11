package com.example.mysubmission_intermediate.UI.Story.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mysubmission_intermediate.Api.StoryResponseItem
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.Remote.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: Repository) : ViewModel() {
    val getAllStories get() = pref.StoriesResponse
    val showLoading get() = pref.showLoading
    val toastText get() = pref.toastText

    fun getAllStories(token: String): LiveData<PagingData<StoryResponseItem>> {
        viewModelScope.launch {
            pref.getAllStories(token)
        }
        return pref.getAllStories(token).cachedIn(viewModelScope)
    }
    fun loadState(): LiveData<UserModel> {
        return pref.loadState()
    }
}