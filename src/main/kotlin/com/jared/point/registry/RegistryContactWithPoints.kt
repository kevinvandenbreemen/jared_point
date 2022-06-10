package com.jared.point.registry

import com.jared.point.client.PointAPI
import com.vandenbreemen.kevincommon.nbl.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistryContactWithPoints {

    private val logger = Logger.getLogger(RegistryContactWithPoints::class.java)

    suspend fun testPoint(point: RegistryItem): Boolean {
        val registryClient: PointAPI = Retrofit.Builder()
            .baseUrl("http://${point.host}:${point.port}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PointAPI::class.java)

        try {
            val result = registryClient.healthCheck()

            logger.debug { "Health check succeeded:  $result" }
            return true
        } catch (ex: Exception) {
            logger.error(ex) { "failed to query point health" }
        }

        return false
    }

}