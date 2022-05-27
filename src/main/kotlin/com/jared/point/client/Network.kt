package com.jared.point.client

import com.jared.point.`interface`.RegistryClient
import com.jared.point.registry.RegistryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.atomic.AtomicReference

class Network(registryUrl: String) {

    private val logger = KotlinLogging.logger {  }

    private val registry: AtomicReference<List<RegistryItem>> = AtomicReference()

    private val registryClient: RegistryClient = Retrofit.Builder()
        .baseUrl(registryUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RegistryClient::class.java)

    fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            registryClient.list().body()?.let {
                registry.set(it)
                logger.info { "Updated the point registry" }
            }
        }
    }

}

fun main() {
    Network("http://localhost:9999").update()
    Thread.sleep(10000)
}