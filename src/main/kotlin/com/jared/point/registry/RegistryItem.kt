package com.jared.point.registry

import kotlinx.serialization.Serializable

@Serializable
data class RegistryItem(val host: String, val port: Int) {



}