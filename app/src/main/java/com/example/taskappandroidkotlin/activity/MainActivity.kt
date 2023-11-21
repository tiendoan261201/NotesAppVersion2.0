package com.example.taskappandroidkotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.taskappandroidkotlin.fragment.HomeFragment
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.databinding.ActivityMainBinding
import com.example.taskappandroidkotlin.fragment.OnBoardFragment
import com.example.taskappandroidkotlin.preference.NotePref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


    }

    fun replaceFragment(fragment: Fragment, isTransaction: Boolean) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isTransaction) {
            fragmentTransaction.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }

    private fun init() {
        val notePref = NotePref(this)
        CoroutineScope(Dispatchers.Main).launch {
            if (notePref.isFirstUsage()) {
                replaceFragment(OnBoardFragment.newInstance(), false)
            } else {
                replaceFragment(HomeFragment.newInstance(), false)
            }
        }
    }
}