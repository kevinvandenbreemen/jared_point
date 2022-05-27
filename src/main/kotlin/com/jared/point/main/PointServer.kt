package com.jared.point.main

import spark.Service
import spark.Service.ignite
import java.net.InetAddress

class PointServer() {

    private val http: Service = ignite().port(8888)

    fun setup() {
        http.get("/health"){ request, response ->

            response.header("test", "test")

            return@get "Up:  ${InetAddress.getLocalHost().hostName}"
        }
    }

}