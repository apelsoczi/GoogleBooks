package com.pelsoczi.googlebookssibs.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pelsoczi.googlebookssibs.ui.books.BooksScreen
import com.pelsoczi.googlebookssibs.ui.navigation.NavigationDestination.BooksDestination

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
            BooksScreen()
        }
    }
}