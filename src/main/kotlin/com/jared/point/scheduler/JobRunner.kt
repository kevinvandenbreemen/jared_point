package com.jared.point.scheduler

import com.vandenbreemen.kevincommon.nbl.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Runs jobs in the background periodically.
 */
class JobRunner {

    companion object {
        val DELAY = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS)
    }

    private val  logger = Logger.getLogger(JobRunner::class.java)

    private val jobs: Vector<()->Unit> = Vector<()->Unit>()
    fun addTask(task: ()->Unit) {
        jobs.add(task)
    }

    fun start() {
        logger.info { "Initiating job runner" }
        run()
    }

    private fun run() {
        CoroutineScope(Dispatchers.Default).launch {
            logger.info { "Running background tasks...." }
            jobs.forEach { action->action() }
            logger.info { "Executed all tasks" }

            delay(DELAY)

        }.invokeOnCompletion {
            run()
        }
    }

}