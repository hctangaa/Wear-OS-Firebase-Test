package com.example.wearosfirebasetest

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.wearosfirebasetest.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        // Check the default value before fetch remote
        Log.d(TAG, "Before fetching")
        Log.d(TAG, "isLookingGood = ${remoteConfig.getBoolean("am_i_look_good")}")

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Success fetching remote config")
                Log.d(TAG, "isLookingGood = ${remoteConfig.getBoolean("am_i_look_good")}")
            } else {
                Log.d(TAG, "Fail fetching remote config")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}