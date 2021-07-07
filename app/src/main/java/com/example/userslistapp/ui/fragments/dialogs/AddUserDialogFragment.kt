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
import com.example.userslistapp.misc.UserCreationValidator
import com.example.userslistapp.misc.viewLifecycle
import com.example.userslistapp.models.appmodels.User
import org.koin.android.ext.android.inject

class AddUserDialogFragment: DialogFragment() {

    companion object {
        fun newInstance() = AddUserDialogFragment().apply {
            arguments = Bundle().apply {
                //put args
            }
        }
    }

    private var actionListener: AddUserDialogActionListener? = null
    private val validator: UserCreationValidator by inject()
    private var userToAdd: User? = null

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
                binding.firstNameTl.error = null
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
                binding.lastNameTl.error = null
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_user, null, false)
            setupWatchers()
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
                    val user = User(
                        binding.firstNameEt.text.toString(),
                        binding.lastNameEt.text.toString(),
                        binding.statusMessageEt.text.toString(),
                    )
                    when(val status = validator.validate(user)) {
                        is UserCreationValidator.ValidationStatus.Valid -> { userToAdd = status.user; dismiss() }
                        is UserCreationValidator.ValidationStatus.Invalid -> {
                            showValidationError(status)
                        }
                    }
                }
                negativeBtn.setOnClickListener {
                    dismiss()
                }
            }
            dialog
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        userToAdd?.let {
            actionListener?.onAdd(it)
        } ?: actionListener?.onAddCancel()
        super.onDismiss(dialog)
    }

    private fun showValidationError(status: UserCreationValidator.ValidationStatus.Invalid) {
        if (!status.isFirstNameValid) {
            binding.firstNameTl.error = "Invalid First Name. Must not be empty."
        }
        if (!status.isLastNameValid) {
            binding.lastNameTl.error = "Invalid Last Name. Must not be empty."
        }
        if (!status.isStatusMessageValid) {
            // no such case, can be empty
        }
    }

}

interface AddUserDialogActionListener {
    fun onAdd(user: User)
    fun onAddCancel()
}