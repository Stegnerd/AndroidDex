package com.stegner.androiddex.dependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * ViewModelFactory which uses Dagger to create instances
 *
 * @Inject - annotate the constructor that dagger should use to create the instances of a class
 * @JvmSuppressWildCards - is used to instruct the compiler to generate or omit wildcards for type arguments
 *                          declaration site-variance.  In the simplest terms Kotlin will take your code that looks like this:
 *                          fun recordActionsWithGps(@Body gpsRequests: List<GpsRequest>)
 *                          And turn it into this:
 *                          fun recordActionsWithGps(@Body gpsRequests: List<? extends GpsRequest>)
 *                          TLDR: gets rid of the ? so explicit types only

 * constructor - the map is a dictionary of kvp, where key is a viewmodel .class and the value is the Provider (builder) of that viewmodel
 */
class PokemonViewModelFactory @Inject constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // if the dictionary contains the KVP for the model needed assign the provider
        var creator: Provider<out ViewModel>? = creators[modelClass]


        if(creator == null){
            // if not loop through viewmodels and find one where it is assignable, and get its provider
            for((key, value) in creators) {
                if(modelClass.isAssignableFrom(key)){
                    creator = value
                    break
                }
            }
        }
        // if still null throw error
        if(creator == null){
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        // cast to that provider to T and return
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}

@Module
internal abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: PokemonViewModelFactory): ViewModelProvider.Factory
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
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)