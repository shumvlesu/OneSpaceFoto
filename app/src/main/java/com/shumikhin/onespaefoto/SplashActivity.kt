package com.shumikhin.onespaefoto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.onespaefoto.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.animate().rotationBy(750f)
            .setInterpolator(LinearInterpolator()).duration = 10000
        //binding.imageView.animate().scaleY(-1f).setInterpolator(AccelerateDecelerateInterpolator()).duration =1000

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}
