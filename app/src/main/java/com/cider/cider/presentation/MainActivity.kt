package com.cider.cider.presentation

import android.os.Bundle
import com.cider.cider.R
import com.cider.cider.databinding.ActivityMainBinding
import com.cider.cider.utils.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}