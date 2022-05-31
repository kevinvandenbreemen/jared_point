package com.jared.point.registry

class Registry {

    private val _items: MutableList<RegistryItem> = mutableListOf()
    val items: List<RegistryItem> get() = _items.toList()

    fun add(item: RegistryItem): Boolean {
        if(_items.contains(item)) {
            return false
        }
        this._items.add(item)
        return true
    }



}