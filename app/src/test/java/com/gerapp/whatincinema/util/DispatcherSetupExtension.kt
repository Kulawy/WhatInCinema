package com.gerapp.whatincinema.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.lang.reflect.Field

typealias TestDispatcherAnnotation = com.gerapp.whatincinema.util.TestDispatcher

/**
 * Custom extension that sets proper dispatcher in @BeforeEach and reset in @AfterEach.
 *
 * Custom dispatcher can be set by using @TestDispatcher annotation in test class.
 * If no custom dispatcher is found UnconfinedTestDispatcher will be used by default.
 *
 * @see UnconfinedTestDispatcher
 */
@ExperimentalCoroutinesApi
class DispatcherSetupExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        val annotatedField = checkForAnnotatedField(context)
        val customDispatcher = getDispatcher(context, annotatedField)

        Dispatchers.setMain(customDispatcher)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }

    private fun checkForAnnotatedField(context: ExtensionContext) =
        context.requiredTestClass.declaredFields.firstOrNull {
            it.isAnnotationPresent(TestDispatcherAnnotation::class.java)
        }

    private fun getDispatcher(context: ExtensionContext, field: Field?) =
        if (field != null && TestDispatcher::class.java.isAssignableFrom(field.type)) {
            field.isAccessible = true
            field.get(context.requiredTestInstance) as TestDispatcher
        } else {
            UnconfinedTestDispatcher()
        }
}
