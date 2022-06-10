package com.jared.point.client

import com.jared.point.`interface`.RegistryClient
import com.jared.point.registry.RegistryItem
import com.vandenbreemen.kevincommon.nbl.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference


class Network(registryUrl: String) {

    private val logger = Logger.Companion.getLogger(Network::class.java)

    private val registry: AtomicReference<List<RegistryItem>> = AtomicReference()

    private val registryClient: RegistryClient by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = (HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(registryUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistryClient::class.java)
    }

    fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            registryClient.list().body()?.let {
                registry.set(it)
                logger.debug { "Updated the point registry to $it" }
            }
        }
    }

    /**
     * Register with the point server
     */
    fun register(localPort: Int) {
        val host = InetAddress.getLocalHost().hostName

        val item = RegistryItem(
            host, localPort
        )

        CoroutineScope(Dispatchers.IO).launch {
            registryClient.register(item)
        }

    }

}