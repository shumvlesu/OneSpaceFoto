package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsEnlargeBinding

class AnimationsActivityOld3 : AppCompatActivity() {

    private var _binding: ActivityAnimationsEnlargeBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsEnlargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_animations_enlarge)
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.container, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height = if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            //В данном случае мы меняем размеры картинки и её расположение внутри ImageView (scaleType)
            binding.imageView.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }
}



