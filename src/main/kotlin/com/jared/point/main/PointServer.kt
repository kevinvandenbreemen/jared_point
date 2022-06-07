package com.jared.point.main

import com.jared.point.model.PointHostData
import com.jared.point.model.PointStatus
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import spark.Service
import spark.Service.ignite
import java.net.InetAddress

class PointServer(private val port: Int) {

    private val http: Service = ignite().port(port)

    private val jsonSerializer = Json { ignoreUnknownKeys=true }

    fun setup() {
        http.get("/health"){ request, response ->

            response.type("application/json")
            return@get jsonSerializer.encodeToString(PointStatus(
                PointHostData(InetAddress.getLocalHost().hostName)
            ))
        }
    }

}