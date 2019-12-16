package com.stegner.androiddex

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * A custom [AndroidJUnitRunner] used to replace the application used in tests
 * with a [TestAndroiddexApplication]
 *
 * This is how tests are instantiated, , instead of starting AndroiddexApplication it starts TestAndroiddexApplication
 */
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestAndroiddexApplication::class.java.name, context)
    }
}