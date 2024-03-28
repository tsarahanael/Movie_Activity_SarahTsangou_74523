package com.stu74523.movie_activity_sarahtsangou_74523

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun AppNavigation(movieViewModel: MovieViewModel, reservations: MutableList<Reservation>) {
    val navController = rememberNavController()

    val movieState by movieViewModel.movieListState.collectAsState()

    NavHost(navController = navController, startDestination = MainScreen.route)
    {
        composable(route = MainScreen.route)
        {
            movieViewModel.fetchMovieListData()
            MainScreen(navControler = navController, movieViewModel)
        }

        composable(
            route = DetailScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        )
        { backStackEntry ->
            val movieId: String = backStackEntry.arguments?.getString("movieId")!!
            movieViewModel.fetchMovieData(movieId)
            DetailsScreen(navControler = navController, movieViewModel = movieViewModel, reservations = reservations)
        }

        composable( route = Reservations.route )
        {
            ReservationsScreen( navControler = navController, reservations = reservations, movieViewModel =  movieViewModel)

        }

        composable(
            route = "error/{message}",
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        )
        { backStackEnty ->
            backStackEnty.arguments?.getString("movieId")
                ?.let { com.stu74523.movie_activity_sarahtsangou_74523.Error(message = it) }
        }
    }
}