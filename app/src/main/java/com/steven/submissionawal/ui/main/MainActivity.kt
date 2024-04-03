package com.steven.submissionawal.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.submissionawal.R
import com.steven.submissionawal.data.pref.SettingPreferences
import com.steven.submissionawal.data.pref.dataStore
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.ActivityMainBinding
import com.steven.submissionawal.ui.adapter.UsersAdapter
import com.steven.submissionawal.ui.viewmodel.DarkViewModel
import com.steven.submissionawal.ui.viewmodel.MainViewModel
import com.steven.submissionawal.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val darkViewModel: DarkViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val layoutManager = LinearLayoutManager(this)

        val itemDecor = DividerItemDecoration(this, layoutManager.orientation)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get((MainViewModel::class.java))

        with(binding){
            binding.rvUsers.layoutManager = layoutManager
            binding.rvUsers.addItemDecoration(itemDecor)

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchView.hide()
                    val query = searchView.text.toString()
                    mainViewModel.findUsers(query)
                    true
                }
        }

        mainViewModel.listUsers.observe(this){users ->
            setUsersData(users)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        darkViewModel.getThemeSettings().observe(this){isDarkModeActive ->
            if(isDarkModeActive){
                setTheme(com.google.android.material.R.style.Theme_Material3_Dark)
            }else{
                setTheme(com.google.android.material.R.style.Theme_Material3_Light)
            }
        }
    }
    private fun setUsersData(users: List<ItemsItem>){
        val adapter = UsersAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_setting -> {
                val intent = Intent(this, DarkModeActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}