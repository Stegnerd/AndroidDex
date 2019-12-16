package com.stegner.androiddex

import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * An application used from instrumentation tests.
 * It has a fragment injector to enable tests using Fragment Scenario
 *
 * This is used to create instances of fragments using supportFragmentInjector
 */
class TestAndroiddexApplication : AndroidDexApplication(), HasSupportFragmentInjector {


    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector

}