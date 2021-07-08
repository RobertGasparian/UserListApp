package com.example.userslistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<UIState>: ViewModel(), UiStateEmitter<UIState> {

    protected val uiState: MutableLiveData<UIState> = MutableLiveData()

    override fun uiState(): LiveData<UIState> = uiState
}

interface UiStateEmitter<UIState> {
    fun uiState(): LiveData<UIState>
}