package com.jared.point.main

import spark.Service
import spark.Service.ignite

class PointServer() {

    private val http: Service = ignite().port(8888)

    fun setup() {
        http.get("/health"){ request, response ->

            response.header("test", "test")
            response.body("fart")

            return@get "Up"
        }
    }

}