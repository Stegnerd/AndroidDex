package com.stegner.androiddex.dependencyinjection.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Factory for creating Workers to be injected
 *
 * @Inject - annotate the constructor that dagger should use to create the instances of a class
 * @JvmSuppressWildCards - is used to instruct the compiler to generate or omit wildcards for type arguments
 *                          declaration site-variance.  In the simplest terms Kotlin will take your code that looks like this:
 *                          fun recordActionsWithGps(@Body gpsRequests: List<GpsRequest>)
 *                          And turn it into this:
 *                          fun recordActionsWithGps(@Body gpsRequests: List<? extends GpsRequest>)
 *                          TLDR: gets rid of the ? so explicit types only
 *
 * constructor - the map is a dictionary of kvp, where key is Worker .class and the value is the Provider (builder) of that worker
 */
class DaggerAwareWorkerFactory  @Inject constructor(private val workerFactoryMap: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<IWorkerFactory<out ListenableWorker>>>): WorkerFactory() {
    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        // look for that Worker class
        val entry = workerFactoryMap.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }

        // if found get the value (which is its factory method)
        val factory = entry?.value
            ?: throw IllegalArgumentException("Could not find worker: $workerClassName")

        // create the Worker with the given workerParameters
        return factory.get().create(workerParameters)
    }

}

/**
 * Creates special keys for the map
 *
 * @Target - used for reflection, expose what things are available for access, in this case functions, sets and gets
 * @Retention - A tag that stays forever with the programming element even after compiled, and it is visible via reflection, in this case yes
 * @MapKey - Dagger2 used for creating a special key value used with reflection to get the type of model used for the key name,
 *           looks like this Class<? extends ViewModel> use KClass for reflection access. ? is used for wildcard, We use JVMSupressWildCard
 *           to ignore that and remain explicit
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)