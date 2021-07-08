package com.example.userslistapp.ui.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.userslistapp.R
import com.example.userslistapp.databinding.DialogAddUserBinding
import com.example.userslistapp.misc.viewLifecycle
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.viewmodels.AddUserVIewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddUserDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = AddUserDialogFragment().apply {
            arguments = Bundle().apply {
                //put args
            }
        }
    }

    private var actionListener: AddUserDialogActionListener? = null
    private var userToAdd: User? = null

    private val viewModel: AddUserVIewModel by viewModel()

    private var binding: DialogAddUserBinding by viewLifecycle()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? AddUserDialogActionListener)?.let {
            actionListener = it
        }
    }


    private fun setupWatchers() {
        binding.firstNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.firstNameChanged(s.toString())
            }
        })
        binding.lastNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.lastNameChanged(s.toString())
            }
        })
    }

    private fun render(uiState: UIState) {
        when (uiState) {
            is UIState.InvalidData -> onInvalidData(
                uiState.isFirstNameValid,
                uiState.isLastNameValid
            )
            is UIState.ValidData -> onValidData(uiState.user)
            UIState.HideFirstNameError -> onFirstNameErrorHide()
            UIState.HideLastNameError -> onLastNameErrorHide()
        }
    }

    private fun onFirstNameErrorHide() {
        binding.firstNameTl.error = null
    }

    private fun onLastNameErrorHide() {
        binding.lastNameTl.error = null
    }

    private fun onInvalidData(isFirstNameValid: Boolean, isLastNameValid: Boolean) {
        if (!isFirstNameValid) {
            binding.firstNameTl.error = getString(R.string.first_name_not_valid_error_msg)
        }
        if (!isLastNameValid) {
            binding.lastNameTl.error = getString(R.string.last_name_not_valid_error_msg)
        }
    }

    private fun onValidData(user: User) {
        userToAdd = user
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_user, null, false)
            setupWatchers()
            observeUiState()
            builder
                .setTitle(getString(R.string.add_new_user))
                .setView(binding.root)
                .setPositiveButton(getString(R.string.add_user), null)
                .setNegativeButton(R.string.cancel, null)
            val dialog = builder.create()
            dialog.setOnShowListener {
                val positiveBtn: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeBtn: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                positiveBtn.setOnClickListener {
                    viewModel.tryToAdd(
                        binding.firstNameEt.text.toString(),
                        binding.lastNameEt.text.toString(),
                        binding.statusMessageEt.text.toString()
                    )
                }
                negativeBtn.setOnClickListener {
                    dismiss()
                }
            }
            dialog
        }
    }

    private fun observeUiState() {
        viewModel.uiState().observe(this, {
            if (it != null) {
                render(it)
            }
        })
    }


    override fun onDismiss(dialog: DialogInterface) {
        userToAdd?.let {
            actionListener?.onAdd(it)
        } ?: actionListener?.onAddCancel()
        super.onDismiss(dialog)
    }

}

interface AddUserDialogActionListener {
    fun onAdd(user: User)
    fun onAddCancel()
}

sealed class UIState {
    data class InvalidData(
        val isFirstNameValid: Boolean,
        val isLastNameValid: Boolean,
    ) : UIState()

    object HideFirstNameError : UIState()
    object HideLastNameError : UIState()
    data class ValidData(val user: User) : UIState()
}