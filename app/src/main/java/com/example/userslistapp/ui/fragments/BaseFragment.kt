package com.example.userslistapp.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.userslistapp.R
import com.example.userslistapp.misc.viewLifecycle
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<UIState, VB: ViewBinding> : Fragment() {

    private val simpleLoadingDialog: Dialog by lazy {
        AppCompatDialog(requireContext(), R.style.UsersListAppTheme_Dialog).apply {
            setContentView(R.layout.dialog_laoding)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    @get:LayoutRes
    abstract val layoutId: Int

    private var _binding: VB by viewLifecycle()
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return _binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupClicks()
        subscribeToViewModel()
    }

    protected open fun showLoading() {
        simpleLoadingDialog.show()
    }

    protected open fun hideLoading() {
        simpleLoadingDialog.dismiss()
    }

    protected open fun showError(message: String? = null) {
        Snackbar.make(requireView(), message ?: getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG)
            .show()
    }

    protected open fun subscribeToViewModel() {

    }

    protected open fun setupViews(view: View) {

    }

    protected open fun setupClicks() {

    }

    abstract fun render(uiState: UIState)
}