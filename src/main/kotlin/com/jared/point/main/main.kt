package com.jared.point.main

import com.jared.point.registry.Registry
import com.jared.point.registry.RegistryItem

fun main(args: Array<String>) {

    if(args.contains("r")) {
        val reg = Registry()
        reg.add(RegistryItem("http://10.0.0.29", 8888))

        RegistryServer(8888, reg).setup()
        println("Registry started \uD83C\uDF85")
        return
    }

    PointServer(8888).setup()
}