package com.example.userslistapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<UIState>(app: Application): AndroidViewModel(app), UiStateEmitter<UIState> {

    protected val uiState: MutableLiveData<UIState> = MutableLiveData()
}

interface UiStateEmitter<UIState> {
    fun uiState(): LiveData<UIState>
}