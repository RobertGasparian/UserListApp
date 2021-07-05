package com.example.userslistapp.ui.navigation

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateTo(fragment: Fragment, withBackStack: Boolean = true, withAnimation: Boolean = true)
    fun back(toTag: String? = null)
}