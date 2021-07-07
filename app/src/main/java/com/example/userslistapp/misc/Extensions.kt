package com.example.userslistapp.misc

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(p1, p2, p3, p4, p5) else null
}

suspend fun UserDao.getUser(user: User): UserDBM {
    return getUserBy(user.firstName, user.lastName)
}

suspend fun UserDao.getUser(person: PersonDTO): UserDBM? {
    val (f, l) = person
    return if (f != null && l != null) {
        getUserBy(f, l)
    } else null
}

fun <T> Fragment.viewLifecycle(onDestroyView: (() -> Unit)? = null): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T>, LifecycleObserver {

        private var binding: T? = null

        init {
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle, Observer { owner: LifecycleOwner? ->
                    owner?.lifecycle?.addObserver(this)
                })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            onDestroyView?.invoke()
            binding = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return binding ?: error("Called before onCreateView or after onDestroyView.")
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            this.binding = value
        }

    }