package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.UserListAdapter
import com.example.myapplication.databinding.FragmentGithubUserListBinding
import com.example.myapplication.result.Result
import com.example.myapplication.viewmodel.GithubViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GithubUserListFragment : Fragment() {

    lateinit var userListAdapter: UserListAdapter
    private var _binding: FragmentGithubUserListBinding? = null

    private val binding get() = _binding!!

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

        _binding = FragmentGithubUserListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeGithubUsers()
        viewModel.getGithubUsers()
    }

    private fun setupRecyclerView() {
        userListAdapter = UserListAdapter { user ->
            findNavController(this).navigate(
                R.id.action_GithubUserListFragment_to_GithubUserDetailFragment,
                bundleOf("username" to user.login)
            )
        }

        binding.userRecyclerView.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { userListAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userListAdapter.filter(newText.orEmpty())
                return true
            }
        })

    }

    private fun observeGithubUsers() {
        lifecycleScope.launchWhenStarted {
            viewModel.userState.collect { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        userListAdapter.submitFullList(result.data)
                    }
                    is Result.Error -> {
                        Log.e("TAG", "${result.exception?.message}")
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
