package com.gerapp.whatincinema.util

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Custom extension that injects all mocks found in test classes (also in @Nested inner classes).
 *
 * Mocking happens in @BeforeEach. unmocking in @AfterEach.
 *
 * @see org.junit.jupiter.api.Nested
 */
class MockkInjectorExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        context.requiredTestInstances.allInstances.forEach {
            MockKAnnotations.init(it)
        }
    }

    override fun afterEach(context: ExtensionContext) {
        unmockkAll()
    }
}
