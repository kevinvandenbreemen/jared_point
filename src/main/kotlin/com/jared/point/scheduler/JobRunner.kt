package com.jared.point.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Runs jobs in the background periodically.
 */
class JobRunner {

    companion object {
        val DELAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
    }

    private val  logger = KotlinLogging.logger {  }

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

            Thread.sleep(DELAY)
            run()
        }
    }

}