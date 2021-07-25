package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsBinding

class AnimationsActivityOld1 : AppCompatActivity() {

    private var _binding: ActivityAnimationsBinding? = null
    private val binding get() = _binding!!
    private var textIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.END))
            textIsVisible = !textIsVisible
            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }

    }
}
