package com.somenthingnice.livecode.features.main

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.somenthingnice.livecode.core.ui.viewBinding.viewBinding
import com.somenthingnice.livecode.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    private val navigator by lazy {
        findNavController(binding.navHost.id)
    }

}