package com.stegner.androiddex

import androidx.work.Configuration
import androidx.work.WorkManager
import com.stegner.androiddex.dependencyinjection.DaggerApplicationComponent
import com.stegner.androiddex.dependencyinjection.workers.DaggerAwareWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class AndroidDexApplication : DaggerApplication() {

    // Our custom worker factory that will override default behavior
    @Inject lateinit var daggerAwareWorkerFactory: DaggerAwareWorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        // TODO set this to be only during debug builds
        Timber.plant(DebugTree())
        configureWorkManager()
    }

    /**
     * Replace default WorkerFactory with our custom one
     */
    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(daggerAwareWorkerFactory)
            .build()

        WorkManager.initialize(this, config)
    }

}