package com.steven.submissionawal.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.submissionawal.R
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.ActivityFavoriteBinding
import com.steven.submissionawal.ui.adapter.FavoriteAdapter
import com.steven.submissionawal.ui.viewmodel.FavoriteViewModel
import com.steven.submissionawal.ui.viewmodel.FavoriteViewModelFactory
import com.steven.submissionawal.utils.Injection

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecylerView()

        val viewModelFactory = FavoriteViewModelFactory(Injection.provideFavoriteRepository(this))
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)


        viewModel.favoriteUser.observe(this) {users ->
            favoriteAdapter.submitList(users)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setRecylerView(){
        favoriteAdapter = FavoriteAdapter()

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFav.visibility = View.VISIBLE
        } else {
            binding.progressBarFav.visibility = View.GONE
        }
    }

}