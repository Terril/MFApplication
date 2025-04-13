package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.GithubUserRepos
import com.example.myapplication.databinding.ItemRepoItemsBinding

class RepositoryAdapter(
    private val onItemClicked: (GithubUserRepos) -> Unit
) : ListAdapter<GithubUserRepos, RepositoryAdapter.ReposViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val binding =
            ItemRepoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReposViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    inner class ReposViewHolder(private val binding: ItemRepoItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GithubUserRepos) {
            binding.repoNameTextView.text = data.name
            binding.repoDescriptionTextView.text =
                data.description ?: itemView.context.getString(R.string.no_description)
            binding.repoLanguageTextView.text =
                data.language ?: itemView.context.getString(R.string.n_a)
            binding.repoStarsTextView.text = data.stargazers_count.toString()

            binding.repoDescriptionTextView.visibility =
                if (data.language.isNullOrBlank()) View.GONE else View.VISIBLE
            binding.repoLanguageTextView.visibility =
                if (data.language.isNullOrBlank()) View.GONE else View.VISIBLE

            binding.root.setOnClickListener {
                onItemClicked(data)
            }
        }
    }

}


class RepoDiffCallback : DiffUtil.ItemCallback<GithubUserRepos>() {
    override fun areItemsTheSame(oldItem: GithubUserRepos, newItem: GithubUserRepos): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUserRepos, newItem: GithubUserRepos): Boolean {
        return oldItem == newItem
    }
}
