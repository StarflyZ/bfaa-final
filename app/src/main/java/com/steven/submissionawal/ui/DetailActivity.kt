package com.steven.submissionawal.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.steven.submissionawal.data.response.DetailUserResponse
import com.steven.submissionawal.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object{
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarUrl")

        val sectionsPagerAdapter = SectionsPagerAdapter(this).apply {
            this.username = username.toString()
        }

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        username?.let { detailViewModel.showFollows(it) }

        with(binding){
            tvUsername.text = username.toString()
            Glide.with(this@DetailActivity)
                .load(avatarUrl)
                .into(ivDetailProfile)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager){ tabLayout, position ->
                tabLayout.text = when(position){
                    0 -> "Followers"
                    1 -> "Following"
                    else -> "unkown"
                }
            }.attach()
        }

        detailViewModel.detailUser.observe(this){ username ->
            getUserDetail(username)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun getUserDetail(user: DetailUserResponse){
        with(binding){
            tvDetailName.text = "${user.name}"
            tvFollowers.text = "${user.followers} Followers"
            tvFollowing.text = "${user.following} Following"
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }
}