package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.adapter.RepositoryAdapter
import com.example.myapplication.adapter.UserListAdapter
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.databinding.FragmentGithubUserDetailBinding
import com.example.myapplication.result.Result
import com.example.myapplication.viewmodel.GithubViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GithubUserDetailFragment : Fragment() {

    private var _binding: FragmentGithubUserDetailBinding? = null

    private val binding get() = _binding!!
    private lateinit var repositoryAdapter: RepositoryAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GithubViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGithubUserDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeGithubUserDetail()
        observeGithubUserRepos()
        arguments?.getString("username")?.let {
            viewModel.getGithubUserDetail(it)
        }
    }

    private fun setupRecyclerView() {
        repositoryAdapter = RepositoryAdapter { data ->
            data.html_url?.let { openRepositoryWebPage(it) }
        }

        binding.repositoriesRecyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeGithubUserDetail() {
        lifecycleScope.launchWhenStarted {
            viewModel.userDetailState.collect { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Log.d("TAG", "${result.data}")
                        loadUserDetail(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Log.e("TAG", "${result.exception?.message}")
                    }
                }
            }
        }
    }

    private fun observeGithubUserRepos() {
        lifecycleScope.launchWhenStarted {
            viewModel.userReposState.collect { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Log.d("TAG", "${result.data}")
                        val filteredList = viewModel.filterUserBasedOnForks(result.data)
                        repositoryAdapter.submitList(filteredList)
                    }
                    is Result.Error -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Log.e("TAG", "${result.exception?.message}")
                    }
                }
            }
        }
    }

    private fun loadUserDetail(data: GithubUserDetail) {
        Glide.with(binding.root.context)
            .load(data.avatar_url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .transform(CircleCrop())
            .into(binding.detailImage)
        binding.detailName.text = data.name
        binding.detailUsername.text = data.login
        binding.detailFollowersNumber.text = data.followers.toString()
        binding.detailFollowingNumber.text = data.following.toString()
    }

    private fun openRepositoryWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        } else {
            // Handle the case where no browser is available
            Toast.makeText(context, "No application found to open web page", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}