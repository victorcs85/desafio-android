package com.picpay.desafio.android.presentation.ui.users.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.data.source.remote.entity.UserResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(userResponse: UserResponse) {
        itemView.name.text = userResponse.name
        itemView.username.text = userResponse.username
        itemView.progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(userResponse.img)
            .error(R.drawable.ic_round_account_circle)
            .into(itemView.picture, object : Callback {
                override fun onSuccess() {
                    itemView.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    itemView.progressBar.visibility = View.GONE
                }
            })
    }
}