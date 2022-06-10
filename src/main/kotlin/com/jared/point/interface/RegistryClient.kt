package com.jared.point.`interface`

import com.jared.point.registry.RegistryItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegistryClient {

    @GET("/list")
    suspend fun list(): Response<List<RegistryItem>>

    @POST("/list")
    suspend fun register(@Body item: RegistryItem): Response<String>

}