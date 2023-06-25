package com.pelsoczi.googlebookssibs.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

sealed interface NavigationDestination {
    val route: String
    val routeWithArgs: String
    val icon: @Composable () -> Unit

    object BooksDestination : NavigationDestination {
        override val route: String = "books_route"
        override val routeWithArgs: String = "$route/"
        override val icon: @Composable () -> Unit = { Icon(Icons.Filled.Home, contentDescription = null) }
    }

    object DetailDestination : NavigationDestination {
        val ARG_ID = "id"
        override val route: String = "detail_route"
        override val routeWithArgs: String = "$route/{$ARG_ID}"
        override val icon: () -> Unit = {}
    }

    object FavoriteDestination : NavigationDestination {
        override val route: String = "favorites_route"
        override val routeWithArgs: String = "$route/"
        override val icon: @Composable () -> Unit = { Icon(Icons.Filled.Favorite, contentDescription = null) }
    }

}

val TOP_LEVEL_DESTINATIONS = listOf(
    NavigationDestination.BooksDestination,
    NavigationDestination.FavoriteDestination,
)