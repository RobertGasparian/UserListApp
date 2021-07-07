package com.example.userslistapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userslistapp.R
import com.example.userslistapp.databinding.FragmentUsersListBinding
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.adapters.UserLongClickListener
import com.example.userslistapp.ui.adapters.UsersAdapter
import com.example.userslistapp.ui.fragments.dialogs.AddUserDialogActionListener
import com.example.userslistapp.ui.fragments.dialogs.AddUserDialogFragment
import com.example.userslistapp.ui.fragments.dialogs.DeleteDialogActionListener
import com.example.userslistapp.ui.fragments.dialogs.DeleteDialogFragment
import com.example.userslistapp.viewmodels.UserListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : BaseFragment<UIState, FragmentUsersListBinding>(), UserLongClickListener,
    DeleteDialogActionListener,
    AddUserDialogActionListener {
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

    private val viewModel: UserListViewModel by viewModel()

    private val adapter = UsersAdapter()

    override fun setupViews(view: View) {
        setupRv()
    }

    private fun setupRv() {
        binding.usersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRv.adapter = adapter
    }

    override fun setupClicks() {
        binding.addFab.setOnClickListener {
            viewModel.tryToAdd()
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
            UIState.AddUserDialog -> onAddUserIntent()
        }
    }

    private fun onDeleteIntent(user: User) {
        DeleteDialogFragment.newInstance(user)
            .show(requireActivity().supportFragmentManager, "delete_tag")
    }

    private fun onAddUserIntent() {
        AddUserDialogFragment.newInstance()
            .show(requireActivity().supportFragmentManager, "add_tag")
    }

    private fun onLoading() {
        showLoading()
    }

    private fun onSuccess(users: List<User>) {
        hideLoading()
        adapter.setUsers(users = users.toTypedArray())
    }

    private fun onError(message: String?) {
        hideLoading()
        showError(message)
    }

    override fun onUserLongClicked(user: User, position: Int) {
        viewModel.tryToDelete(user)
    }

    override fun onDelete(user: User) {
        viewModel.deleteUser(user)
    }

    override fun onDeleteCancel() {
        viewModel.cancelDeleteAction()
    }

    override fun onAdd(user: User) {
        viewModel.addUser(user.firstName, user.lastName, user.statusMessage)
    }

    override fun onAddCancel() {
        viewModel.cancelAddAction()
    }
}

sealed class UIState {
    object Loading : UIState()
    data class Error(val message: String?) : UIState()
    data class Success(val users: List<User>) : UIState()
    data class DeleteDialog(val user: User) : UIState()
    object AddUserDialog : UIState()
}