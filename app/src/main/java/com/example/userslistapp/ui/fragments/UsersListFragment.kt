package com.example.userslistapp.ui.fragments

import android.os.Bundle
import com.example.userslistapp.R

class UsersListFragment: BaseFragment() {
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
}