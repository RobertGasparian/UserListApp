package com.example.userslistapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userslistapp.R
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.adapters.UserLongClickListener
import com.example.userslistapp.ui.adapters.UsersAdapter
import com.example.userslistapp.viewmodels.UserListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

class UsersListFragment : BaseFragment<UIState>(), UserLongClickListener {
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

    private val adapter = UsersAdapter()

    //TODO change to binding (synthetics are deprecated ((( )
    private lateinit var usersRv: RecyclerView
    private lateinit var addFab: FloatingActionButton

    override fun setupViews(view: View) {
        super.setupViews(view)
        usersRv = view.findViewById(R.id.usersRv)
        addFab = view.findViewById(R.id.addFab)
        setupRv()
    }

    private fun setupRv() {
        usersRv.layoutManager = LinearLayoutManager(requireContext())
        usersRv.adapter = adapter
    }

    override fun setupClicks() {
        addFab.setOnClickListener {
            //show add dialog
        }
        adapter.userLongClickListener = this
    }

    override fun subscribeToViewModel() {
        viewModel.uiState().observe(viewLifecycleOwner, {
            if (it != null) {
                render(it)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUsers()
    }

    override fun render(uiState: UIState) {
        when (uiState) {
            is UIState.Error -> onError(uiState.message)
            UIState.Loading -> onLoading()
            is UIState.Success -> onSuccess(uiState.users)
            is UIState.DeleteDialog -> onDeleteIntent(uiState.user)
        }
    }

    private fun onDeleteIntent(user: User) {
        //show delete dialog
    }

    private fun onLoading() {
        //show loading
    }

    private fun onSuccess(users: List<User>) {
        //dismiss load
        adapter.setUsers(users = users.toTypedArray())
    }

    private fun onError(message: String?) {
        //dismiss loading
        //show error
    }

    override fun onUserLongClicked(user: User, position: Int) {
        viewModel.tryToDelete(user)
    }
}

sealed class UIState {
    object Loading : UIState()
    class Error(val message: String?) : UIState()
    class Success(val users: List<User>): UIState()
    class DeleteDialog(val user: User): UIState()
}