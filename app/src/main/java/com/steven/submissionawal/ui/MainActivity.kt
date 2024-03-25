package com.steven.submissionawal.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
    }
    private fun setUsersData(users: List<ItemsItem>){
        val adapter = UsersAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}