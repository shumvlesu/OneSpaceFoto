package com.shumikhin.onespaefoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shumikhin.onespaefoto.ui.PictureOfTheDayFragment
import com.shumikhin.onespaefoto.ui.setting.SETTINGS_SHARED_PREFERENCES
import com.shumikhin.onespaefoto.ui.setting.THEME_RES_ID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setTheme(
            getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.PinkTheme)
        )
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}