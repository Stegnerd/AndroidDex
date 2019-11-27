package com.stegner.androiddex.dependencyinjection.workers

import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 * Interface for all Worker Factories for injection
 *
 * Here is details on how this solution was implemented:
 * https://stackoverflow.com/questions/52434165/dagger2-unable-to-inject-dependencies-in-workmanager
 */
interface IWorkerFactory<T: ListenableWorker> {
    fun create(params: WorkerParameters) : T
}