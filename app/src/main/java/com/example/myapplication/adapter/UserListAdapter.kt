package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.R
import com.example.myapplication.data.GithubUser
import com.example.myapplication.databinding.ItemUserBinding

class UserListAdapter(
    private val onItemClicked: (GithubUser.Item) -> Unit
) : ListAdapter<GithubUser.Item, UserListAdapter.UserViewHolder>(UserDiffCallback()) {
    private var fullList: List<GithubUser.Item> = listOf()

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser.Item) {
            binding.userNameTextView.text = user.login

            Glide.with(binding.root.context)
                .load(user.avatar_url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(CircleCrop())
                .into(binding.userAvatarImageView)

            binding.root.setOnClickListener {
                onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    fun submitFullList(list: List<GithubUser.Item>) {
        fullList = list
        submitList(list)
    }

    fun filter(query: String) {
        val filtered = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.login.contains(query, ignoreCase = true)
            }
        }
        submitList(filtered)
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<GithubUser.Item>() {
    override fun areItemsTheSame(oldItem: GithubUser.Item, newItem: GithubUser.Item): Boolean {
        return oldItem.id == newItem.id // Check if items represent the same entity
    }

    override fun areContentsTheSame(oldItem: GithubUser.Item, newItem: GithubUser.Item): Boolean {
        return oldItem == newItem // Check if item contents are identical
    }
}
