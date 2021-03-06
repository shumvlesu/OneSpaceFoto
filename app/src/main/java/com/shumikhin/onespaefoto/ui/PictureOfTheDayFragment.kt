package com.shumikhin.onespaefoto.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.*
import android.webkit.WebSettings
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.*
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shumikhin.onespaefoto.MainActivity
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.BottomSheetLayoutBinding
import com.shumikhin.onespaefoto.databinding.MainFragmentBinding
import com.shumikhin.onespaefoto.ui.animations.AnimationsActivityBonus
import com.shumikhin.onespaefoto.ui.api.ApiActivity
import com.shumikhin.onespaefoto.ui.apibottom.ApiBottomActivity
import com.shumikhin.onespaefoto.ui.picture.BottomNavigationDrawerFragment
import com.shumikhin.onespaefoto.ui.picture.PictureOfTheDayData
import com.shumikhin.onespaefoto.ui.setting.SettingsFragment
import com.shumikhin.onespaefoto.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private var bindingSh: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var isExpanded = false

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        //И в фрагменте и в incude дал одно и тоже id "@+id/bottom_sheet_container"
        //Иначе вылетатет приложение
        bindingSh = binding.bottomSheetContainer
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // по нажатию на иконку переходим в браузер и открывать нужную страницу в «Википедии»
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        //по нажатию меняем размер изображения
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                //binding.container, TransitionSet()
                binding.mainFragment, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height = if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            //В данном случае мы меняем размеры картинки и её расположение внутри ImageView (scaleType)
            binding.imageView.scaleType = if (isExpanded) ImageView.ScaleType.FIT_CENTER else ImageView.ScaleType.CENTER
        }

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
        //setBottomAppBar(view.findViewById(R.id.bottom_app_bar))
    }


    //метод, в который будем передавать наш BottomSheet и инициализировать bottomSheetBehavior.
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state =
            BottomSheetBehavior.STATE_COLLAPSED //В свернутое состояние, но не скрытое
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_fav -> activity?.let {startActivity(Intent(it, ApiBottomActivity::class.java))}
            //R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            //R.id.app_bar_search -> activity?.let {startActivity(Intent(it, AnimationsActivity::class.java))}
            R.id.app_bar_search -> activity?.let {startActivity(Intent(it, AnimationsActivityBonus::class.java))}
            R.id.app_bar_settings -> activity?.apply {
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, SettingsFragment())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            //Запускает активити ApiActivity
            R.id.app_bar_api -> activity?.let {
                startActivity(
                    Intent(
                        it,
                        ApiActivity::class.java
                    )
                )
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        //context.setSupportActionBar(view)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(
                this@PictureOfTheDayFragment,
                Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }

                }

                //Сделал по аналогии с url
                val explanation = serverResponseData.explanation
                if (!explanation.isNullOrEmpty()) {
                    //Здеь грузим описание
                    //Ну раз есть описание, наверняка есть и заголовок, не будем проверять на заполненность
                    //bindingSh?.bottomSheetDescriptionHeader?.text = serverResponseData.title.toString()
                    bindingSh?.mainToolbar?.title = serverResponseData.title.toString()

                    //Описание под фотографией
                    bindingSh?.bottomSheetContainer?.let {TransitionManager.beginDelayedTransition(it)}
                    //bindingSh?.bottomSheetDescription?.text = serverResponseData.explanation.toString()
                    bindingSh?.bottomSheetDescription?.let {
                        val spannableText = SpannableString(serverResponseData.explanation.toString())
                        spannableText.setSpan(ForegroundColorSpan(Color.RED),0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        spannableText.setSpan( AbsoluteSizeSpan(40, true),0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        it.text = spannableText
                        it.typeface = Typeface.createFromAsset(context?.assets,"fonts/FallingSkyBlack-GYXA.otf")
                    }

                    //bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }

            }
            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true //
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bindingSh = null
    }
}

