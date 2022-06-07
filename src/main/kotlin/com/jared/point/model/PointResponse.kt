package com.jared.point.model

import kotlinx.serialization.Serializable

@Serializable
data class PointHostData(val hostName: String)

@Serializable
data class PointStatus(
            val hostData: PointHostData
                         )
