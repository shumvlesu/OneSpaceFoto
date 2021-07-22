package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationsBinding? = null
    private val binding get() = _binding!!
    private var textIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_animations)
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer)
            textIsVisible = !textIsVisible
            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }
    }
}
