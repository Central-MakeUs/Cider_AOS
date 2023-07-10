package com.cider.cider.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.cider.cider.R
import com.cider.cider.databinding.ActivityMainBinding
import com.cider.cider.presentation.challenge.ChallengeHomeFragment
import com.cider.cider.presentation.register.LoginFragment
import com.cider.cider.utils.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fl_main) as NavHostFragment
        val navController = navHostFragment.navController*/
    }
}