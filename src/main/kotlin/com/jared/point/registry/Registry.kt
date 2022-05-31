package com.jared.point.registry

import java.util.concurrent.Semaphore

class Registry {

    private val semaphore = Semaphore(1)

    private val _items: MutableList<RegistryItem> = mutableListOf()
    val items: List<RegistryItem> get() = _items.toList()

    fun add(item: RegistryItem): Boolean {
        if(!semaphore.tryAcquire()){
            return false
        }

        try {
            if (_items.contains(item)) {
                return false
            }
            this._items.add(item)
            return true
        } finally {
            semaphore.release()
        }
    }



}