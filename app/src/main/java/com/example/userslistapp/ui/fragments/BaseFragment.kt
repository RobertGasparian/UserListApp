package com.example.userslistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<UIState>: Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupClicks()
        subscribeToViewModel()
    }

    protected open fun subscribeToViewModel() {

    }

    protected open fun setupViews(view: View) {

    }

    protected open fun setupClicks() {

    }

    abstract fun render(uiState: UIState)
}