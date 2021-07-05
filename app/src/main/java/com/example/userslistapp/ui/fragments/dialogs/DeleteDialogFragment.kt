package com.example.userslistapp.ui.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.userslistapp.R
import com.example.userslistapp.models.appmodels.User

class DeleteDialogFragment: DialogFragment() {

    companion object {
        private const val USER_KEY = "user_key"

        fun newInstance(user: User) = DeleteDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(USER_KEY, user)
            }
        }
    }

    private var actionListener: DeleteDialogActionListener? = null
    private var needToDelete: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? DeleteDialogActionListener)?.let {
            actionListener = it
        }
    }

    private fun getMessage(): String {
        val user = arguments?.getSerializable(USER_KEY) as? User
        return user?.let {
            val statusMessage = it.statusMessage
            var message = "Do you really want to delete ${it.firstName} ${it.lastName}"
            message += if (statusMessage.isEmpty()) {
                "?"
            } else {
                ", with status message: ${statusMessage}?"
            }
            message
        } ?: getString(R.string.something_went_wrong)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(getMessage())
                .setPositiveButton(getString(R.string.delete)) { dialog, id ->
                    needToDelete = true
                    dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, id ->
                    needToDelete = false
                    dismiss()
                }
            builder.create()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        val user = arguments?.getSerializable(USER_KEY) as? User
        if (needToDelete && user != null) actionListener?.onDelete(user) else actionListener?.onDeleteCancel()
        super.onDismiss(dialog)
    }
}

interface DeleteDialogActionListener {
    fun onDelete(user: User)
    fun onDeleteCancel()
}