package com.shumikhin.onespaefoto.ui.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityApiBinding
import com.shumikhin.onespaefoto.databinding.MainFragmentBinding

class ApiActivity : AppCompatActivity() {

    private var _binding: ActivityApiBinding? = null
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = ActivityApiBinding.inflate(inflater, container, false)
//        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
//        return binding.getRoot()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

}