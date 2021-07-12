package com.shumikhin.onespaefoto.ui.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.onespaefoto.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private var _binding: ActivityApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewPagerAdapter(supportFragmentManager).also { binding.viewPager.adapter = it }
    }

}