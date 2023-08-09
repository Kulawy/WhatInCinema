package com.gerapp.whatincinema.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gerapp.whatincinema.base.Route
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsScreen
import com.gerapp.whatincinema.ui.movies.MoviesScreen
import timber.log.Timber

@Composable
fun WhatInCinemaAppScreen() {
    val navController = rememberNavController()
    WhatInCinemaNavHost(
        navController = navController,
    )
}

@Composable
fun WhatInCinemaNavHost(
    navController: NavHostController,
) {
//    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            Timber.d("NAVIGATE TO MOVIES")
            MoviesScreen(
                navigateToMovieDetails = {
                    navController.navigate(route = Route.Detail.createRoute(it))
                },
            )
        }
        composable(
            Route.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                },
            ),
        ) {
            MovieDetailsScreen(
                onBackClick = { navController.navigateUp() },
                movieId = it.arguments?.getInt("movieId") ?: -1,
            )
        }
    }
}
