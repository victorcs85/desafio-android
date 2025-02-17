package com.picpay.desafio.android.presentation.ui.users.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.picpay.desafio.android.core.extensions.loadImage
import com.picpay.desafio.android.data.source.remote.entity.UserResponse
import com.picpay.desafio.android.databinding.UserItemBinding
import com.picpay.desafio.android.domain.model.User
import javax.sql.DataSource

class UsersAdapter : ListAdapter<User, UsersAdapter.UserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = UserViewHolder(
        UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(
        itemView: UserItemBinding,
    ) : RecyclerView.ViewHolder(itemView.root) {

        private val userItemName = itemView.tvName
        private val userItemUsername = itemView.tvUsername
        private val userItemPicture = itemView.ivPicture
        private val progressBar = itemView.pbUser

        fun bind(user: User) = with(user) {
            userItemName.text = name
            userItemUsername.text = username
            userItemPicture.loadImage(
                imageUrl = img,
                transformCircle = true,
                callback = object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}