package com.jared.point.main

import spark.Service
import spark.Service.ignite
import java.net.InetAddress

class PointServer(private val port: Int) {

    private val http: Service = ignite().port(port)

    fun setup() {
        http.get("/health"){ request, response ->

            response.header("test", "test")

            return@get "Point\n" +
                    "=======\nUp:  ${InetAddress.getLocalHost().hostName}"
        }
    }

}