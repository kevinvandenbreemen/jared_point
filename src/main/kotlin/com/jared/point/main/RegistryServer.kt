package com.jared.point.main

import com.jared.point.registry.Registry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import spark.Service
import java.net.InetAddress

/**
 * Registry of points
 */
class RegistryServer(private val port: Int, private val registry: Registry) {

    private val http: Service = Service.ignite().port(port)

    private val jsonSerializer = Json { ignoreUnknownKeys=true }

    fun setup() {
        http.get("/health"){ request, response ->

            response.header("test", "test")

            return@get "Registry\n=======\nUp:  ${InetAddress.getLocalHost().hostName}"
        }

        http.get("/list") {request, response ->
            response.type("application/json")
            return@get jsonSerializer.encodeToString(registry.items)
        }

    }

}