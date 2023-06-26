package com.cider.cider.presentation

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cider.cider.R
import com.cider.cider.presentation.register.LoginActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                moveToLogin()
                finish()
            }

            override fun onPermissionDenied(deniedPermissions: List<String?>) {
                Log.d("DeniedPermissions", "DeniedPermissions : $deniedPermissions")
                moveToLogin()
                finish()
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(POST_NOTIFICATIONS)
            .check()
    }

    private fun moveToLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}