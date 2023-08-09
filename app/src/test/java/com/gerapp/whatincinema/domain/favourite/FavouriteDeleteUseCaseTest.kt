package com.gerapp.whatincinema.domain.favourite

import com.gerapp.whatincinema.domain.repository.FavouriteRepository
import com.gerapp.whatincinema.util.DispatcherSetupExtension
import com.gerapp.whatincinema.util.MockkInjectorExtension
import com.gerapp.whatincinema.util.TestDispatcher
import com.gerapp.whatincinema.util.coVerifyOnce
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(DispatcherSetupExtension::class, MockkInjectorExtension::class)
internal class FavouriteDeleteUseCaseTest {

    @TestDispatcher
    private val testCoroutineDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    lateinit var repository: FavouriteRepository

    @InjectMockKs
    private lateinit var favouriteDeleteUseCase: FavouriteDeleteUseCase

    @Test
    fun `Check that useCase trigger repository call`() = runTest {
        val testMovieId = 123
        favouriteDeleteUseCase(testMovieId).single()

        coVerifyOnce { repository.removeMovieFormFavourites(testMovieId) }
        confirmVerified(repository)
    }
}
