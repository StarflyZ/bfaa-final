package com.steven.submissionawal.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: DetailViewModel
    private val adapter = FollowAdapter()
    private var position: Int = 0
    private var username: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        if(position == 1){
            viewModel.getFollowers(username)
            viewModel.listfollowers.observe(viewLifecycleOwner){listfollowers ->
                Log.d("FollowFragment", "Followers data: $listfollowers")
                setFollows(listfollowers)
            }
        }else{
            viewModel.getFollowing(username)
            viewModel.listfollowing.observe(viewLifecycleOwner){listfollowing ->
                Log.d("FollowFragment", "Following data: $listfollowing")
                setFollows(listfollowing)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }


    private fun setFollows(username: List<ItemsItem>){
        binding.rvFollows.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollows.adapter = adapter
        username?.let {
            adapter.submitList(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }
}