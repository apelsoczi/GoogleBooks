package com.pelsoczi.googlebookssibs.ui.favorites

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pelsoczi.googlebookssibs.data.Repository
import com.pelsoczi.googlebookssibs.data.local.bookFromDTO
import com.pelsoczi.googlebookssibs.data.remote.model.BookItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FavoritesViewModelTest {

    private val repository = mockk<Repository>()

    lateinit var viewModel: FavoritesViewModel

    @Test
    fun `load favorites books from repository`() = runTest {
        // given
        val favorites = listOf(BookItem(id = "6DiXzQEACAAJ").bookFromDTO())
        coEvery { repository.favorites() } returns flowOf(favorites)
        // when
        viewModel = FavoritesViewModel(
            repository = repository,
        )
        viewModel.viewState.test {
            // then
            skipItems(1)
            awaitItem().let {
                assertThat(it).isNotEmpty()
                assertThat(it.first().identifier == favorites.first().identifier)
            }
        }
    }

}