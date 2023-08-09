package com.gerapp.whatincinema.data.network.interceptor

import com.gerapp.whatincinema.data.network.CustomHeader.AUTHORIZATION
import com.gerapp.whatincinema.data.network.CustomHeader.BEARER
import com.gerapp.whatincinema.data.network.persistance.AuthPersistentStorage
import com.gerapp.whatincinema.util.MockkInjectorExtension
import com.gerapp.whatincinema.util.relaxedMockk
import com.gerapp.whatincinema.util.verifyOnce
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockkInjectorExtension::class)
internal class AuthorizationInterceptorTest {

    @RelaxedMockK
    lateinit var authPersistentStorage: AuthPersistentStorage

    @InjectMockKs
    lateinit var sut: AuthorizationInterceptor

    @Test
    fun `Check if authorization request header is build by adding authorization header`() {
        val (chain: Interceptor.Chain, builder: Request.Builder) = mockChain()

        val token = "token"
        every { authPersistentStorage.accessToken } returns token

        sut.intercept(chain)

        verifyOnce {
            builder.addHeader(AUTHORIZATION, "$BEARER $token")
            authPersistentStorage.accessToken
        }
        confirmVerified(authPersistentStorage, builder)
    }

    private fun mockChain(): Pair<Interceptor.Chain, Request.Builder> {
        val builder: Request.Builder = relaxedMockk()

        val request: Request = relaxedMockk {
            every { newBuilder() } returns builder
        }

        val chain: Interceptor.Chain = relaxedMockk {
            every { request() } returns request
        }

        return chain to builder
    }
}
