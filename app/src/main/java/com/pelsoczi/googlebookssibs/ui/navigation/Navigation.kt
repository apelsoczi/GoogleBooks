package com.pelsoczi.googlebookssibs.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pelsoczi.googlebookssibs.ui.books.BooksScreen
import com.pelsoczi.googlebookssibs.ui.detail.DetailScreen
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.BooksDestination
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.DetailDestination
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.DetailDestination.ARG_ID

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()
    Scaffold { paddingValues ->
        AppNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BooksDestination.route,
        modifier = modifier,
    ) {
        composable(route = BooksDestination.route) {
            BooksScreen(
                onClickBook = {
                    navController.navigate(
                        route = "${DetailDestination.route}/${it.identifier}",
                    ) {

                    }
                }
            )
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ARG_ID) { type = NavType.StringType }
            )
        ) {
            DetailScreen(
                navController = navController
            )
        }
    }
}