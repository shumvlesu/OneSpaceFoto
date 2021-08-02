package com.shumikhin.onespaefoto.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shumikhin.onespaefoto.R
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerItemEarthBinding
import com.shumikhin.onespaefoto.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<Data>
) : RecyclerView.Adapter<BaseViewHolder>() {



    //Обратите внимание на аргументы:
    //мы получаем не только parent, но и viewType. Это ключевой параметр, с помощью которого мы и
    //будем «надувать» тот или иной layout и отображать те или иные данные.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_EARTH) {
            EarthViewHolder(
                ActivityRecyclerItemEarthBinding.inflate(inflater, parent,false)
            )
        } else {
            MarsViewHolder(
                ActivityRecyclerItemMarsBinding.inflate(inflater, parent,false)
            )
        }
    }

    //В методе onBindViewHolder происходит аналогичный процесс: с помощью позиции элемента можно узнать
    //его viewType и отобразить во ViewHolder соответствующие данные.
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_EARTH) {
            holder as EarthViewHolder
            holder.bind(dataItem[position])
        } else {
            holder as MarsViewHolder
            holder.bind(dataItem[position])
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
        return if (data[position].someDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
    }

    //inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    inner class EarthViewHolder(val binding: ActivityRecyclerItemEarthBinding) : BaseViewHolder(binding.root){
        override fun bind(dataItem: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.descriptionTextView.text = dataItem.first.someDescription
                binding.wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(dataItem.first )
                }
            }
        }
    }

    //inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    inner class MarsViewHolder(val binding: ActivityRecyclerItemMarsBinding) : BaseViewHolder(binding.root){
        override fun bind(dataItem: Pair<Data, Boolean>) {
            binding.marsImageView.setOnClickListener {
                onListItemClickListener.onItemClick(dataItem.first)
            }
        }
    }

    //Создадим слушатель нажатий на элемент списка. Это будет простой интерфейс, который возвращает данные нажатого элемента.
    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    companion object {
        //константы TYPE_EARTH и TYPE_MARS для
        //разных типов элементов в списке
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
    }

}