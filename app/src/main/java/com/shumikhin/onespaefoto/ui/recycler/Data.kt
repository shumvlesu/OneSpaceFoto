package com.shumikhin.onespaefoto.ui.recycler

//модель данных для нашего списка
const val TYPE_HEADER = 0
const val TYPE_EARTH = 1
const val TYPE_MARS = 2

data class Data(
    var someText: String = "",
    var someDescription: String? = "",
    var deployed: Boolean = false,
    val type: Int = TYPE_MARS
) {
    var id: Int = currentId

    init {
        currentId++
    }

    companion object {
        var currentId = 0
        fun initId() {
            currentId = 0
        }
    }
}
