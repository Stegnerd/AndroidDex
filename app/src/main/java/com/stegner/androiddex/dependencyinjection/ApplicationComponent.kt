package com.stegner.androiddex.dependencyinjection

import android.content.Context
import com.stegner.androiddex.AndroidDexApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component for the application
 *
 * AndroidSupportInjectionModule is used to allow the component to expose : HasActivityInjector, HasServiceInjector and HasFragmentInjector
 * https://medium.com/tompee/dagger-for-android-a-detailed-guide-b7df2f40c044
 *
 * See the `TestApplicaitonComponent` used in UI tests
 *
 * @Singleton - means the component only gets instantiated once
 * @Component - This annotation is used to build the interface which wires everything together
 *              and is a something like a bridge between @Module and @Inject
 *
 * @AndroidInjector - Performs members-injection for a concrete subtype of a core Android type (e.g., Activity or Fragment).
 * @Component.Factory - A factory is a type with a single method that returns a new component instance each time it is called.
 *                      The parameters of that method allow the caller to provide the modules, dependencies and bound instances
 *                      required by the component.
 * @BindsInstance - Allows Context to be passed into the component when it is injected
 *
 * Great analogy - components are neat and ready to use chucks for the program, they are the transport system
 * where as modules are the vegetables for the recipe
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class])
interface ApplicationComponent : AndroidInjector<AndroidDexApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}