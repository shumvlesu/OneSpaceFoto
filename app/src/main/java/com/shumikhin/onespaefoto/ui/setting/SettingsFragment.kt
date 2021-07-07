package com.shumikhin.onespaefoto.ui.setting

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.FragmentSettingsBinding


const val SETTINGS_SHARED_PREFERENCES = "SettingsSharedPreferences"
const val THEME_RES_ID = "ThemeResID"
private const val THEME_NAME_SHARED_PREFERENCES = "ThemeNameSharedPreferences"
const val MY_DEFAULT_THEME = 0
const val MY_CUSTOM_THEME_PINK = 1
const val My_CUSTOM_THEME_COSMIC = 2

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var currentTheme: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setSharedPreferencesSettings()
        binding.defaultTheme.setOnClickListener {
            if (currentTheme != MY_DEFAULT_THEME) {
                requireActivity().apply {
                    setTheme(R.style.Theme_OneSpaсeFoto)
                    recreate()
                    saveThemeSettings(MY_DEFAULT_THEME, R.style.Theme_OneSpaсeFoto)
                }
            }
        }
        binding.pinkTheme.setOnClickListener {
            if (currentTheme != MY_CUSTOM_THEME_PINK) {
                requireActivity().apply {
                    setTheme(R.style.PinkTheme)
                    recreate()
                    saveThemeSettings(MY_CUSTOM_THEME_PINK, R.style.PinkTheme)
                }
            }
        }
        binding.cosmicTheme.setOnClickListener {
            if (currentTheme != My_CUSTOM_THEME_COSMIC) {
                requireActivity().apply {
                    setTheme(R.style.CosmicTheme)
                    recreate()
                    saveThemeSettings(My_CUSTOM_THEME_COSMIC, R.style.CosmicTheme)
                }
            }
        }
    }

    private fun setSharedPreferencesSettings() {
        activity?.let {
            currentTheme =
                it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                    .getInt(THEME_NAME_SHARED_PREFERENCES, MY_DEFAULT_THEME)
            when (currentTheme) {
                MY_CUSTOM_THEME_PINK -> {
                    binding.pinkTheme.isChecked = true
                }
                My_CUSTOM_THEME_COSMIC -> {
                    binding.cosmicTheme.isChecked = true
                }
                else -> {
                    binding.defaultTheme.isChecked = true
                }
            }
        }
    }

    private fun saveThemeSettings(currentTheme: Int, style: Int) {
        this.currentTheme = currentTheme
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE).edit()) {
                putInt(THEME_NAME_SHARED_PREFERENCES, currentTheme).apply()
                putInt(THEME_RES_ID, style).apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}