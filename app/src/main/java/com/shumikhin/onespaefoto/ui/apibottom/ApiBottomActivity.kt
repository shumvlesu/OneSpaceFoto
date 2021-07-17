package com.shumikhin.onespaefoto.ui.apibottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityApiBottomBinding
import com.shumikhin.onespaefoto.ui.api.EarthFragment
import com.shumikhin.onespaefoto.ui.api.MarsFragment
import com.shumikhin.onespaefoto.ui.api.WeatherFragment

class ApiBottomActivity : AppCompatActivity() {

    private var _binding: ActivityApiBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_api_bottom)
        _binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container,WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)
        val badge = binding.bottomNavigationView.getBadge(R.id.bottom_view_earth)
        badge?.maxCharacterCount = 2
        badge?.number = 999

        //если пользователь повторно нажал на тот же элемент
        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                }
            }
        }


    }
}
