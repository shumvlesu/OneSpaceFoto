package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.transition.*
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsEnlargeBinding
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsPathTransitionsBinding
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsShuffleBinding

class AnimationsActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationsShuffleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsShuffleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //В методе createViews мы программно создаём TextView и добавляем в LinearLayout. Этот
        //процесс вам должен быть знаком по первому курсу. В методе onCreate мы создаём список текста,
        //который мы будем перемешивать. Далее в TransitionManager мы передаём контейнер, в котором
        //нужно анимировать элементы. В нашем случае это LinearLayout, который содержит динамически
        //изменяемый набор из TextView. Как только в контейнере меняется набор элементов,
        //TransitionManager смотрит, совпадают ли уникальные id новых TextView со старыми. Если да, то
        //происходит плавное перемещение старого элемента на новое место.
        //Этот способ подходит не только для TextView, но и для любого другого виджета.
        val titles: MutableList<String> = ArrayList()
        for (i in 0..4) {
            titles.add(String.format("Item %d", i + 1))
        }
        createViews(binding.transitionsContainer, titles)
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,
                ChangeBounds())
            titles.shuffle()
            createViews(binding.transitionsContainer, titles)
        }
    }
    private fun createViews(layout: ViewGroup, titles: List<String>) {
        layout.removeAllViews()
        for (title in titles) {
            val textView = TextView(this)
            textView.text = title
            textView.gravity = Gravity.CENTER_HORIZONTAL
            ViewCompat.setTransitionName(textView, title)
            layout.addView(textView)
        }
    }
}
