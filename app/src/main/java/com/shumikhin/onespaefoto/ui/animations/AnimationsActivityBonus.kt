package com.shumikhin.onespaefoto.ui.animations

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsBonusStartBinding

class AnimationsActivityBonus : AppCompatActivity() {

    private var _binding: ActivityAnimationsBonusStartBinding? = null
    private val binding get() = _binding!!
    private var show = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_animations_bonus_start)
        _binding = ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backgroundImage.setOnClickListener {
            if (show) hideComponents() else
                showComponents()
        }
    }

    //Вся магия происходит в методе showComponents. Для начала мы создаём экземпляр класса
    //ConstraintSet. Это класс, который позволяет программно создавать constraints для вашего layout.
    //Далее мы заполняем его constraints конечного экрана через clone. Далее мы используем уже
    //знакомые нам классы Transition и TransitionManager: создаём Transition типа ChangeBounds
    //(изменение границ view) и в качестве Interpolator устанавливаем AnticipateOvershootInterpolator.

    //Interpolator — это вспомогательный класс внутри Transition, который определяет уровень изменений в
    //анимациях, таких как прозрачность, перемещение, изменение размера. Благодаря Interpolator эти
    //анимации можно ускорять, повторять и т. д.

    //AnticipateOvershootInterpolator, как следует из названия, позволяет добиться анимации
    //отскока, когда анимированный элемент сначала перепрыгивает конечную точку анимации, затем
    //движется немного назад, а затем вперёд — и замирает. Как будто элемент закреплён на пружине,
    //которая сжимается и разжимается несколько раз, прежде чем поставить элемент на место. Благодаря
    //этому интерполятору мы можем произвести такие анимации сразу над всеми элементами без лишнего
    //кода. Далее указываем длительность анимации и вызываем у TransitionManager знакомый нам
    //метод beginDelayedTransition. Не забываем, что у ConstraintSet тоже нужно вызвать метод
    //applyTo чтобы анимация стартовала.

    //Внимание! Приложение упадёт, если в нашем контейнере constraint_container будут view без id. Id
    //нужен даже там, где вы не используете какой-то view напрямую. То же самое касается view,
    //которые вы создаете динамически. Им тоже нужно присвоить id программно, чтобы анимации
    //запустились.

    private fun showComponents() {
        show = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(
            binding.constraintContainer,
            transition
        )
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun hideComponents() {
        show = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_start)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(
            binding.constraintContainer,
            transition
        )
        constraintSet.applyTo(binding.constraintContainer)
    }

}

