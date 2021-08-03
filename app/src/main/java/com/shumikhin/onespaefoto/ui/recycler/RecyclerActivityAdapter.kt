package com.shumikhin.onespaefoto.ui.recycler

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerItemEarthBinding
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerItemHeaderBinding
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    //private var data: List<Data>
    private var data: MutableList<Data>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>(), ItemTouchHelperAdapter,
    AutoUpdatableAdapter {


    //Обратите внимание на аргументы:
    //мы получаем не только parent, но и viewType. Это ключевой параметр, с помощью которого мы и
    //будем «надувать» тот или иной layout и отображать те или иные данные.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = ActivityRecyclerItemEarthBinding.inflate(inflater, parent, false)
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding = ActivityRecyclerItemMarsBinding.inflate(inflater, parent, false)
                MarsViewHolder(binding)
            }
            else -> {
                val binding = ActivityRecyclerItemHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    //В методе onBindViewHolder происходит аналогичный процесс: с помощью позиции элемента можно узнать
    //его viewType и отобразить во ViewHolder соответствующие данные.
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        if (getItemViewType(position) == TYPE_EARTH) {
//            holder as EarthViewHolder
//            holder.bind(data[position])
//        } else {
//            holder as MarsViewHolder
//            holder.bind(data[position])
//        }
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val combinedChange = createCombinedPayload(payloads as List<Change<Data>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            if (newData.someText != oldData.someText) {
                holder.changeSomeText(newData.someText)
            }
        }
    }

    //А getItemCount остаётся без изменений.
    override fun getItemCount(): Int {
        return data.size
    }

    //Метод, благодаря которому мы можем всегда знать, какого типа элемент нам нужно отобразить в списке
    override fun getItemViewType(position: Int): Int {
        //В нашем случае мы придерживаемся внутренней договорённости, что если описания нет или оно пустое, значит, элемент
        //относится к типу «Марс»
        //return if (data[position].someDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
        return data[position].type
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition == 1 && toPosition == 0) return
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        if (position > 0) {
            data.removeAt(position)
            notifyItemRemoved(position)
        } else notifyItemChanged(position)
    }

    fun setItems(newItems: List<Data>) {
        //val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        //result.dispatchUpdatesTo(this)
        autoNotify(data, newItems) { oldItem, newItem -> oldItem.id == newItem.id }
        data.clear()
        data.addAll(newItems)
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem(): Data {
        val data = Data("Mars")
        Log.e("my", "ID = ${data.id}")
        return data
    }

    inner class DiffUtilCallback(
        private var oldItems: List<Data>,
        private var newItems: List<Data>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        // https://habr.com/ru/post/337774/
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return Change(
                oldItem,
                newItem
            )
        }
    }


    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder {

        abstract fun bind(dataItem: Data)
        abstract fun changeSomeText(text: String)

        fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(dataItem: Data) {
            binding.root.setOnClickListener {
                notifyItemChanged(1, Data("Jupiter"))
            }
        }

        override fun onItemSelected() {}
        override fun onItemClear() {}
        override fun changeSomeText(text: String) {}
    }

    inner class EarthViewHolder(val binding: ActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {

        override fun changeSomeText(text: String) {}

        override fun bind(dataItem: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.descriptionTextView.text = dataItem.someDescription
                binding.wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(
                        dataItem
                    )
                }
            }
        }
    }

    inner class MarsViewHolder(val binding: ActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        override fun bind(dataItem: Data) {
            binding.root.setOnClickListener { onListItemClickListener.onItemClick(dataItem) }
            binding.addItemImageView.setOnClickListener { addItem() }
            binding.removeItemImageView.setOnClickListener { removeItem() }
            binding.moveItemDown.setOnClickListener { moveDown() }
            binding.moveItemUp.setOnClickListener { moveUp() }
            if (dataItem.deployed) binding.marsDescriptionTextView.visibility =
                View.VISIBLE else binding.marsDescriptionTextView.visibility = View.GONE
            binding.marsTextView.text = dataItem.someText
            binding.marsTextView.setOnClickListener { toggleText() }

            binding.dragHandleImageView.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        override fun changeSomeText(text: String) {
            binding.marsTextView.text = text
        }

        private fun toggleText() {
            data[layoutPosition].deployed = data[layoutPosition].let {
                !it.deployed
            }
            notifyItemChanged(layoutPosition)
        }
    }


    //Создадим слушатель нажатий на элемент списка. Это будет простой интерфейс, который возвращает данные нажатого элемента.
    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }


}