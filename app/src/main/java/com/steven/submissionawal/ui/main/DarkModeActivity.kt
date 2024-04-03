package com.steven.submissionawal.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.steven.submissionawal.R
import com.steven.submissionawal.data.pref.SettingPreferences
import com.steven.submissionawal.data.pref.dataStore
import com.steven.submissionawal.ui.viewmodel.DarkViewModel
import com.steven.submissionawal.ui.viewmodel.ViewModelFactory


class DarkModeActivity : AppCompatActivity() {
    private lateinit var switchTheme: SwitchMaterial
    private val darkViewModel: DarkViewModel by viewModels {
        ViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        switchTheme = findViewById(R.id.switch_theme)
        darkViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            darkViewModel.saveThemeSetting(isChecked)
        }
    }
}