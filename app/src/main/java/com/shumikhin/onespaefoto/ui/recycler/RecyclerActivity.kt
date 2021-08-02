package com.shumikhin.onespaefoto.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private var _binding: ActivityRecyclerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRecyclerBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_recycler)
        setContentView(binding.root)
        val data = arrayListOf(
            Data("Earth"),
            Data("Earth"),
            Data("Mars", ""),
            Data("Earth"),
            Data("Earth"),
            Data("Earth"),
            Data("Mars", null)
        )

        binding.recyclerView.adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )
    }
}