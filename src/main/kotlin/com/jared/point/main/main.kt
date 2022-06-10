package com.jared.point.main

import com.jared.point.client.Network
import com.jared.point.registry.Registry
import com.jared.point.registry.RegistryItem
import com.jared.point.scheduler.JobRunner
import com.vandenbreemen.kevincommon.cmd.CommandLineParameters
import com.vandenbreemen.kevincommon.nbl.Logger
import mu.KotlinLogging
import java.lang.Exception
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val params = CommandLineParameters(args)

    val logger = Logger.getLogger("main")

    //  Subsystems.  For now these will be initialized here and then meted out to other components
    //  as the system is built up
    val jobRunner = JobRunner().also { it.start() }


    val port = params.getArgument("p")?.let {
        try {
            it.toInt()
        } catch (e: Exception) {
            null
        }
    } ?: 8888
    if(!params.flag("r")) {
        params.addAtLeast("reg", "url of point registry (including its port)")
    } else {
        params.addAtLeast("r", "Flag indicating you want to run the registry on this server")
    }



    //  Setup logic proper

    //  Are we the registry?
    if(params.flag("r")) {
        val reg = Registry()

        RegistryServer(port, reg).setup()
        logger.info{"Registry started \uD83C\uDF85"}
        return
    }

    //  Otehrwise we're a regular point and not the registry
    if(!params.validate()) {
        System.err.println(params.document())
        exitProcess(1)
        return
    }

    //  Set up the network for later
    val network = Network(params.getArgument("reg"))
    jobRunner.addTask {
        network.update()
    }
    jobRunner.addTask {
        network.register(port)
    }

    PointServer(port).setup()
    logger.info { "Point client started ⯍" }
}