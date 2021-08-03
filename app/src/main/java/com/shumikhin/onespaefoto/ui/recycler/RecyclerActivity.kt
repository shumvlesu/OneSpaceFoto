package com.shumikhin.onespaefoto.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private var _binding: ActivityRecyclerBinding? = null
    private val binding get() = _binding!!
    lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerActivityAdapter
    private var isNewList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRecyclerBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_recycler)
        setContentView(binding.root)
//        val data = arrayListOf(
//            Data("Header", type = TYPE_HEADER),
//            Data("Earth"),
//            Data("Earth"),
//            Data("Mars", ""),
//            Data("Earth"),
//            Data("Earth"),
//            Data("Earth"),
//            Data("Mars", null)
//        )
//
//        binding.recyclerView.adapter = RecyclerActivityAdapter(
//            object : RecyclerActivityAdapter.OnListItemClickListener {
//                override fun onItemClick(data: Data) {
//                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
//                }
//            },
//            data,
//            object : OnStartDragListener {
//                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
//                    itemTouchHelper.startDrag(viewHolder)
//                }
//            }
//        )

        val data = arrayListOf(
            Data("Mars")
        )
        data.add(0, Data("Header", type = TYPE_HEADER))
        adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        //adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.recyclerView.adapter = adapter
        binding.recyclerFAB.setOnClickListener { adapter.appendItem() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerDiffUtilFAB.setOnClickListener { changeAdapterData() }


    }

    private fun changeAdapterData() {
        adapter.setItems(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Data> {
        Data.initId()
        return when (instanceNumber) {
            false -> listOf(
                Data("Header", type = TYPE_HEADER),
                Data("Mars"),
                Data("Mars"),
                Data("Mars"),
                Data("Mars"),
                Data("Mars"),
                Data("Mars")
            )
            true -> listOf(
                Data("Header", type = TYPE_HEADER),
                Data("Mars"),
                Data("Jupiter"),
                Data("Mars"),
                Data("Neptun"),
                Data("Saturn"),
                Data("Mars")
            )
        }
    }



}