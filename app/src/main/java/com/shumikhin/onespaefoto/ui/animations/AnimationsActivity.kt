package com.shumikhin.onespaefoto.ui.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsFabBinding

class AnimationsActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationsFabBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false

    //Такой способ анимации объектов устарел: он очень многословен, всё нужно делать вручную.
    //Но он всё ещё не потерял своей эффективности и, возможно, ещё сможет вам пригодиться для
    //каких-то простых анимаций или поддержки старых проектов.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsFabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFAB()
    }

    private fun setFAB() {
        setInitialState()
        binding.fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() {
        binding.transparentBackground.apply {
            alpha = 0f
        }
        binding.optionTwoContainer.apply {
            alpha = 0f
            isClickable = false
        }
        binding.optionOneContainer.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true

        // Вызываем метод ObjectAnimator ofFloat, который позволяет изменять состояние виджета
        //от одного значения (во float) до другого.
        // Последовательно мы передаём в этот метод сначала иконку с параметром "rotation". В
        //следующем параметре указываем, на сколько градусов по часовой стрелке мы хотим
        //провернуть иконку
        ObjectAnimator.ofFloat(binding.plusImageview, "rotation", 0f, 225f).start()
        //Затем передаём контейнеры с опциями и параметрами "translationY" — это движение по
        //вертикали вверх. Первый контейнер будет подниматься чуть выше второго.
        ObjectAnimator.ofFloat(binding.optionTwoContainer, "translationY", -130f).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, "translationY",-250f).start()

        // Изменяем свойства контейнеров с помощью метода animate: меняем прозрачность 0 на 1 с
        //длительностью в 300 миллисекунд и вешаем слушатель конца анимации. Когда анимация
        //закончится, мы сделаем контейнеры кликабельными и повесим соответствующие слушатели
        //нажатий.
        binding.optionTwoContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Изменяем свойство затенения: делаем его непрозрачным и кликабельным. Это позволяет нам
                    //эффективно запретить взаимодействие с любыми элементами, находящимися под
                    //затенением.
                    //При повторном нажатии осуществляем все анимации с точностью до наоборот
                    binding.optionTwoContainer.isClickable = true
                    binding.optionTwoContainer.setOnClickListener {
                        Toast.makeText(this@AnimationsActivity, "Option 2",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        binding.optionOneContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionOneContainer.isClickable = true
                    binding.optionOneContainer.setOnClickListener {
                        Toast.makeText(this@AnimationsActivity, "Option 1",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        binding.transparentBackground.animate()
            .alpha(0.9f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.transparentBackground.isClickable = true
                }
            })
    }

    private fun collapseFab() {
        isExpanded = false
        // Вызываем метод ObjectAnimator ofFloat, который позволяет изменять состояние виджета
        //от одного значения (во float) до другого.
        // Последовательно мы передаём в этот метод сначала иконку с параметром "rotation". В
        //следующем параметре указываем, на сколько градусов по часовой стрелке мы хотим
        //провернуть иконку
        ObjectAnimator.ofFloat(binding.plusImageview, "rotation", 0f, -180f).start()
        //Затем передаём контейнеры с опциями и параметрами "translationY" — это движение по
        //вертикали вверх. Первый контейнер будет подниматься чуть выше второго.
        ObjectAnimator.ofFloat(binding.optionTwoContainer, "translationY", 0f).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, "translationY", 0f).start()

        // Изменяем свойства контейнеров с помощью метода animate: меняем прозрачность 0 на 1 с
        //длительностью в 300 миллисекунд и вешаем слушатель конца анимации.
        binding.optionTwoContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionTwoContainer.isClickable = false
                    binding.optionOneContainer.setOnClickListener(null)
                }
            })

        binding.optionOneContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.optionOneContainer.isClickable = false
                }
            })

        binding.transparentBackground.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.transparentBackground.isClickable = false
                }
            })
    }
}


