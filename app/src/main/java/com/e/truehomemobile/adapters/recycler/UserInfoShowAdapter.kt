package com.e.truehomemobile.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.R
import com.e.truehomemobile.models.user.User
import com.e.truehomemobile.viewHolders.UserInfoShowViewHolder
import kotlinx.android.synthetic.main.user_info_show.view.*

class UserInfoShowAdapter(val user: User): RecyclerView.Adapter<UserInfoShowViewHolder>() {
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_info_show, parent, false)
        return UserInfoShowViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UserInfoShowViewHolder, position: Int) {
        holder.view.user_details_first_name_text_view.text = user.firstName
        holder.view.user_details_last_name_text_view.text = user.lastName
        holder.view.user_details_username_text_view.text = user.username
        holder.view.user_details_email_text_view.text = user.emailAddress
        holder.view.user_details_age_text_view.text = user.age.toString()
        holder.view.user_details_phone_text_view.text = user.telephoneNumber
        holder.view.user_details_address_text_view.text = user.street + " " + user.streetNumber
    }
}