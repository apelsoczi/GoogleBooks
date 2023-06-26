package com.pelsoczi.googlebookssibs.ui.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.ui.detail.DetailViewIntent.AddToFavorites
import com.pelsoczi.googlebookssibs.ui.detail.DetailViewIntent.RemoveFavorite
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.DetailDestination.ARG_ID
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DetailViewModelTest {

    private val savedStateHandle = mockk<SavedStateHandle>()
    private val repository = mockk<Repository>()
    private val context = mockk<Context>()

    private lateinit var detailViewModel: DetailViewModel

    @Test
    fun `verify initial state`() = runTest {
        // given
        coEvery { repository.cachedBook(any()) } returns mockk()
        coEvery { repository.isBookFavorited(any()) } returns flowOf(false)
        // when
        detailViewModel = DetailViewModel(
            savedStateHandle = SavedStateHandle(mapOf(ARG_ID to "6DQACwAAQBAJ")),
            repository = repository,
            context = context,
        )
        detailViewModel.viewState.test {
            // then
            awaitItem().let {
                assertThat(it).isNull()
            }
            skipItems(1)
        }
    }

    @Test
    fun `verify toggling favorite`() = runTest {
        // given
        val delayedTestFlow = flowOf(
            false,  // initial - not favorite
            true,   // favorite
            false   // not favorite
        ).onEach { delay(100) }

        coEvery { repository.cachedBook(any()) } returns mockk()
        coEvery { repository.isBookFavorited(any()) } returns delayedTestFlow
        coEvery { repository.addFavorite(any()) } returns Unit
        coEvery { repository.removeFavorite(any()) } returns Unit
        // when
        detailViewModel = DetailViewModel(
            savedStateHandle = SavedStateHandle(mapOf(ARG_ID to "6DQACwAAQBAJ")),
            repository = repository,
            context = context,
        )
        detailViewModel.viewState.test {
            // then
            skipItems(1)
            awaitItem().let {
                assertThat(it?.isFavorite).isFalse()
            }
            // and when
            detailViewModel.handle(AddToFavorites)
            // and then
            awaitItem().let {
                assertThat(it?.isFavorite).isTrue()
            }
            // and when
            detailViewModel.handle(RemoveFavorite)
            // and then
            awaitItem().let {
                assertThat(it?.isFavorite).isFalse()
            }
        }
    }

}