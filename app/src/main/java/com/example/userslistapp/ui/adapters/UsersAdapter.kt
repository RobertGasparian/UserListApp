package com.example.userslistapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userslistapp.R
import com.example.userslistapp.models.appmodels.User

class UsersAdapter: RecyclerView.Adapter<UserViewHolder>() {
    private val users = mutableListOf<User>()
    var userLongClickListener: UserLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user,parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnLongClickListener {
            userLongClickListener?.onUserLongClicked(users[position], position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = users.size

    fun setUsers(vararg users: User) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun deleteUsers(vararg users: User) {
        this.users.removeAll(users)
        notifyDataSetChanged()
    }
}

class UserViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
    val displayNameTv: TextView = itemView.findViewById(R.id.displayNameTv)
    val statusMessageTv: TextView = itemView.findViewById(R.id.statusMessageTv)

    fun bind(user: User) {
        displayNameTv.text = "${user.firstName} ${user.lastName}"
        statusMessageTv.text = user.statusMessage
    }
}

interface UserLongClickListener {
    fun onUserLongClicked(user: User, position: Int)
}