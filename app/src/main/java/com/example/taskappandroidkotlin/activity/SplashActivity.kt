package com.example.taskappandroidkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.databinding.SplashActivityBinding

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000
    private lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        },SPLASH_DELAY)
    }
}