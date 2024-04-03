package com.steven.submissionawal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.submissionawal.data.pref.SettingPreferences
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DarkViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DarkViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DarkViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}