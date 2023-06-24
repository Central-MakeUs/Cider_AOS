package com.cider.cider.presentation

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cider.cider.R
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
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }
}