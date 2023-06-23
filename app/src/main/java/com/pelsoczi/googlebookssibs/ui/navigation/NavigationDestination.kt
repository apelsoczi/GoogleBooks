package com.pelsoczi.googlebookssibs.ui.navigation

sealed interface NavigationDestination {
    val route: String
    val routeWithArgs: String

    object BooksDestination : NavigationDestination {
        override val route: String = "books_route"
        override val routeWithArgs: String = "$route/"
    }

    object DetailDestination : NavigationDestination {
        val ARG_ID = "id"
        override val route: String = "detail_route"
        override val routeWithArgs: String = "$route/{$ARG_ID}"
    }

}