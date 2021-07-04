package com.example.userslistapp.ui.activities

import android.os.Bundle
import com.example.userslistapp.R
import com.example.userslistapp.ui.fragments.UsersListFragment
import com.example.userslistapp.ui.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : BaseActivity() {
    override val contentId: Int
        get() = R.layout.activity_main
    override val rootId: Int
        get() = R.id.root_layout
    override val navigator: Navigator by inject(named(this::class.simpleName!!)) { parametersOf(this, rootId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.navigateTo(UsersListFragment.newInstance())
    }
}