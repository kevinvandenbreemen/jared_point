package com.jared.point.registry

import com.vandenbreemen.kevincommon.nbl.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Semaphore

class Registry {

    private val logger = Logger.getLogger(Registry::class.java)

    private val semaphore = Semaphore(1)

    private val _items: MutableList<RegistryItem> = mutableListOf()
    val items: List<RegistryItem> get() = _items.toList()

    private val registryContactWithPoints = RegistryContactWithPoints()

    fun register(item: RegistryItem) {
        if(items.contains(item)) {
            logger.debug { "Item at ${item.host}:${item.port} already registered.  No action needed" }
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            if(registryContactWithPoints.testPoint(item)) {
                logger.info { "Successfully verified point at ${item.host}:${item.port}.  Registering!" }
                add(item)
            } else {
                logger.error { "No point found at $item" }
            }
        }
    }

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