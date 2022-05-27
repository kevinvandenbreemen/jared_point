package com.jared.point.main

import com.jared.point.registry.Registry
import com.jared.point.registry.RegistryItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

fun main() {

    val reg = Registry()

    RegistryServer(8888, reg).setup()
}