package com.example.userslistapp.ui.fragments

import android.os.Bundle
import com.example.userslistapp.R
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.viewmodels.UserListViewModel
import org.koin.android.ext.android.inject

class UsersListFragment : BaseFragment<UIState>() {
    companion object {
        fun newInstance(): UsersListFragment {
            return UsersListFragment().apply {
                arguments = Bundle().apply {
                    //put args
                }
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_users_list

    private val viewModel: UserListViewModel by inject()

    override fun subscribeToViewModel() {
        viewModel.uiState().observe(viewLifecycleOwner, {
            if (it != null) {
                render(it)
            }
        })
    }

    override fun render(uiState: UIState) {
        when (uiState) {
            is UIState.Error -> onError(uiState.message)
            UIState.Loading -> onLoading()
            is UIState.Success -> onSuccess(uiState.users)
        }
    }

    private fun onLoading() {

    }

    private fun onSuccess(users: List<User>) {

    }

    private fun onError(message: String) {

    }
}

sealed class UIState {
    object Loading : UIState()
    class Error(val message: String) : UIState()
    class Success(val users: List<User>): UIState()
}