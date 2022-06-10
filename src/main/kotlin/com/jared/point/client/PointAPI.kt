package com.jared.point.client

import com.jared.point.model.PointStatus
import retrofit2.http.GET

interface PointAPI {

    @GET("/health")
    suspend fun healthCheck(): PointStatus

}