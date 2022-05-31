package com.jared.point.main

import com.jared.point.registry.Registry
import com.jared.point.registry.RegistryItem
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import spark.Service
import java.net.InetAddress

/**
 * Registry of points
 */
class RegistryServer(private val port: Int, private val registry: Registry) {

    private val http: Service = Service.ignite().port(port)

    private val jsonSerializer = Json { ignoreUnknownKeys=true }

    private val logger = KotlinLogging.logger {  }

    fun setup() {
        http.get("/health"){ request, response ->

            response.header("test", "test")

            return@get "Registry\n=======\nUp:  ${InetAddress.getLocalHost().hostName}"
        }

        http.get("/list") {request, response ->
            response.type("application/json")
            return@get jsonSerializer.encodeToString(registry.items)
        }

        http.post("/list") {request, response ->

            try {
                (jsonSerializer.decodeFromString(
                    RegistryItem.serializer(),
                    request.body()
                ) as? RegistryItem)?.let { registryItem ->
                    if (!registry.add(registryItem)) {
                        response.status(409)
                        return@post "already registered"
                    }
                    return@post ""
                } ?: response.status(400)
            } catch (jsonDecodingEx: SerializationException) {
                logger.error (jsonDecodingEx) {"Failed to parse incoming data"}
                response.status(400)
            }

            return@post "Missing client node info"

        }

    }

}