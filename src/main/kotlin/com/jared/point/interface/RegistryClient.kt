package com.jared.point.`interface`

import com.jared.point.registry.RegistryItem
import retrofit2.Response
import retrofit2.http.GET

interface RegistryClient {

    @GET("/list")
    suspend fun list(): Response<List<RegistryItem>>

}