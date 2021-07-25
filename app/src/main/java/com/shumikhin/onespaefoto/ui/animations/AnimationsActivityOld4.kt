package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsEnlargeBinding
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsPathTransitionsBinding

class AnimationsActivityOld4 : AppCompatActivity() {

    private var _binding: ActivityAnimationsPathTransitionsBinding? = null
    private val binding get() = _binding!!
    private var toRightAnimation = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsPathTransitionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_animations_enlarge)
        //Класс ChangeBounds принадлежит к пакету анимаций, как и ArcMotion. Он предназначен для
        //отображения анимации перемещения по плавным траекториям. Укажем длительность анимации —
        //500 миллисекунд. Далее используем знакомый нам TransitionManager. Передаём в него контейнер,
        //в границах которого будем анимировать объект, и экземпляр класса ChangeBounds с описанной
        //анимацией. После этого нам остаётся применить параметры для нашей кнопки. TransitionManager
        //и его метод beginDelayedTransition берут на себя всё остальное: плавное перемещение кнопки из
        //текущих координат в новые.
        binding.button.setOnClickListener {
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = 500
            TransitionManager.beginDelayedTransition(
                binding.transitionsContainer,
                changeBounds
            )
            toRightAnimation = !toRightAnimation
            val params = binding.button.layoutParams as FrameLayout.LayoutParams
            params.gravity =
                if (toRightAnimation) Gravity.END or Gravity.BOTTOM else
                    Gravity.START or Gravity.TOP
            binding.button.layoutParams = params
        }
    }
}


