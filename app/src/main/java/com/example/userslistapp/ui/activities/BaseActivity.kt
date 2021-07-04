package com.example.userslistapp.ui.activities

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.userslistapp.ui.navigation.Navigator

abstract class BaseActivity: AppCompatActivity() {
    @get:LayoutRes
    abstract val contentId: Int

    @get:IdRes
    abstract val rootId: Int

    abstract val navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentId)
    }
}