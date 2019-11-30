package com.stegner.androiddex.dependencyinjection.workers

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Dagger module for all Worker factories
 *
 * Actually multibinds the factory. When more workers are required add them here for binding
 */
@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(DataBaseSeedWorker::class)
    fun bindDataBaseSeedWorker(factory: DataBaseSeedWorker.Factory): IWorkerFactory<out ListenableWorker>
}
