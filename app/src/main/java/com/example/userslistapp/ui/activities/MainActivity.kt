package com.example.userslistapp.ui.activities

import android.os.Bundle
import com.example.userslistapp.R
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.fragments.UsersListFragment
import com.example.userslistapp.ui.fragments.dialogs.DeleteDialogActionListener
import com.example.userslistapp.ui.navigation.MainActivityNavigator
import com.example.userslistapp.ui.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : BaseActivity(), DeleteDialogActionListener {
    override val contentId: Int
        get() = R.layout.activity_main
    override val rootId: Int
        get() = R.id.root_layout
    //TODO: need to figure out how to pass fragmentManager
//    override val navigator: Navigator by inject(named(this::class.simpleName!!)) { parametersOf(this.supportFragmentManager, rootId) }
    override val navigator: Navigator = MainActivityNavigator(supportFragmentManager, rootId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.navigateTo(UsersListFragment.newInstance())
    }

    override fun onDelete(user: User) {
        supportFragmentManager.fragments.forEach {
            if (it is DeleteDialogActionListener) {
                it.onDelete(user)
            }
        }
    }

    override fun onCancel() {
        supportFragmentManager.fragments.forEach {
            if (it is DeleteDialogActionListener) {
                it.onCancel()
            }
        }
    }
}