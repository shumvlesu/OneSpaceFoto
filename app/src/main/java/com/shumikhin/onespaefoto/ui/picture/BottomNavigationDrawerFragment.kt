package com.shumikhin.onespaefoto.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.BottomNavigationLayoutBinding
import com.shumikhin.onespaefoto.ui.recycler.RecyclerActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.bottom_navigation_layout, container,false)
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.getRoot()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                R.id.navigation_three -> activity?.let {startActivity(Intent(it, RecyclerActivity::class.java))}
            }
            dismiss()
            true
        }
    }
}