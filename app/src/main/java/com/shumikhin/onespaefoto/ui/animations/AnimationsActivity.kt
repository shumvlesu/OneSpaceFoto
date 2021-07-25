package com.shumikhin.onespaefoto.ui.animations

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityAnimationsExplodeBinding

class AnimationsActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationsExplodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsExplodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_animations_explode)
        binding.recyclerView.adapter = Adapter()
    }

    //Пустой адаптер, пустой холдер. Вся магия у нас происходит по клику на элемент списка в методе
    //Explode. Разберёмся, что в нём происходит. Для начала нам понадобится прямоугольник с
    //координатами по углам экрана. Внутри этого прямоугольника мы запустим нашу анимацию, в
    //процессе которой виджеты улетят за границы прямоугольника, то есть экрана. Для этого мы создадим
    //класс Rect и передадим ему координаты при помощи метода View.getGlobalVisbleRect. Затем
    //мы создаём Transition типа Explode и вешаем на неё EpicenterCallback, который мы
    //упоминали выше. Он возвращает прямоугольник с границами нашего экрана. Далее устанавливаем
    //длительность анимации и вызываем уже знакомый нам метод beginDelayedTransition.
//    private fun explode(clickedView: View) {
//        val viewRect = Rect()
//        clickedView.getGlobalVisibleRect(viewRect)
//        val explode = Explode()
//        explode.epicenterCallback = object : Transition.EpicenterCallback() {
//            override fun onGetEpicenter(transition: Transition): Rect {
//                return viewRect
//            }
//        }
//        explode.duration = 1000
//        TransitionManager.beginDelayedTransition(binding.recyclerView, explode)
//        binding.recyclerView.adapter = null
//    }

    //Теперь усложним поведение и добавим больше настроек: создадим свой сет анимаций. Изменим
    //метод Explode
    //Теперь view, на который мы кликаем, остаётся на месте и исчезает, в то время как все остальные
    //кнопки разлетаются в разные стороны. Более того, мы теперь можем поймать момент, когда анимация
    //заканчивается, и сделать что-то. В данном случае мы просто закрываем экран.
    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                    onBackPressed()
                }
            })
        TransitionManager.beginDelayedTransition(binding.recyclerView, set)
        binding.recyclerView.adapter = null
    }


    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animations_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}


