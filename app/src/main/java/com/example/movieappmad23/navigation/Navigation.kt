package com.example.movieappmad23.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.factories.AddMovieScreenViewModelFactory
import com.example.movieappmad23.factories.DetailScreenViewModelFactory
import com.example.movieappmad23.factories.FavoriteScreenViewModelFactory
import com.example.movieappmad23.factories.HomeScreenViewModelFactory
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.screens.*
import com.example.movieappmad23.viewmodels.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())

    val homeFactory = HomeScreenViewModelFactory(repository)
    val favoriteFactory = FavoriteScreenViewModelFactory(repository)
    val addMovieFactory = AddMovieScreenViewModelFactory(repository)
    val detailFactory = DetailScreenViewModelFactory(repository)

    val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = homeFactory)
    val favoriteScreenViewModel: FavoriteScreenViewModel = viewModel(factory = favoriteFactory)
    val addMovieScreenViewModel: AddMovieScreenViewModel = viewModel(factory = addMovieFactory)
    val detailScreenViewModel: DetailScreenViewModel = viewModel(factory = detailFactory)

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController, moviesViewModel = homeScreenViewModel)
        }

        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, moviesViewModel = favoriteScreenViewModel)
        }
        
        composable(Screen.AddMovieScreen.route) {
            AddMovieScreen(navController = navController, moviesViewModel = addMovieScreenViewModel)
        }

        // build a route like: root/detail-screen/id=34
        composable(
            Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) { backStackEntry ->    // backstack contains all information from navhost
            DetailScreen(navController = navController,
                moviesViewModel = detailScreenViewModel,
                movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY))   // get the argument from navhost that will be passed
        }
    }
}