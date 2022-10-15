package com.example.mysubmission_intermediate.UI.Story.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysubmission_intermediate.Remote.Repository
import kotlinx.coroutines.launch

class LogoutViewModel(private val pref: Repository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}