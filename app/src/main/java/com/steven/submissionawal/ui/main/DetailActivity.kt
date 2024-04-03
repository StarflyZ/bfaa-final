package com.steven.submissionawal.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.steven.submissionawal.R
import com.steven.submissionawal.data.response.DetailUserResponse
import com.steven.submissionawal.database.entity.FavoriteUser
import com.steven.submissionawal.database.room.FavoriteDao
import com.steven.submissionawal.database.room.FavoriteRoomDatabase
import com.steven.submissionawal.databinding.ActivityDetailBinding
import com.steven.submissionawal.ui.viewmodel.DetailViewModel
import com.steven.submissionawal.ui.adapter.SectionsPagerAdapter
import com.steven.submissionawal.ui.viewmodel.DaoViewModel
import com.steven.submissionawal.ui.viewmodel.DaoViewModelFactory
import com.steven.submissionawal.utils.Injection

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModelFactory: DaoViewModelFactory
    private lateinit var viewModel:DaoViewModel
    private lateinit var favoriteUser: FavoriteDao
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory  = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory ).get(DaoViewModel::class.java)

        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarUrl")

        val sectionsPagerAdapter = SectionsPagerAdapter(this).apply {
            this.username = username.toString()
        }

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

        favoriteUser = FavoriteRoomDatabase.getDatabase(application).favoriteDao()

        username?.let { detailViewModel.showFollows(it) }

        if (username != null) {
            favoriteUser.getFavoriteUserByUsername(username).observe(this){ favoriteUser ->
                isFavorite = favoriteUser != null
                buttonFavUpdate()
            }
        }

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
            fabFav.setOnClickListener {
                val username = intent.getStringExtra("username") ?: ""
                val avatarUrl = intent.getStringExtra("avatarUrl") ?: ""

                if(isFavorite){
                    viewModel.deleteFavoriteUser(username)
                    Toast.makeText(this@DetailActivity, "User berhasil di hapus", Toast.LENGTH_SHORT).show()
                }else{
                    val favoriteuser = FavoriteUser(username = username, avatarUrl = avatarUrl)
                    viewModel.insertFavUser(favoriteuser)
                    Toast.makeText(this@DetailActivity, "User ditambahkan sebagai favorite", Toast.LENGTH_SHORT).show()
                }
            }
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
            tvFollowers.setText(resources.getString(R.string.follower, user.followers))
            tvFollowing.setText(resources.getString(R.string.following, user.following))
        }
    }

    private fun buttonFavUpdate(){
        val icon = if(isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        binding.fabFav.setImageResource(icon)
    }
    private fun showLoading(state: Boolean) { binding.progressBar3.visibility = if (state) View.VISIBLE else View.GONE }
}