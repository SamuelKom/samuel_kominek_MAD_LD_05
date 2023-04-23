package com.example.movieappmad23.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val repository: MovieRepository): ViewModel() {
    private val _favoriteMovies = MutableStateFlow(listOf<Movie>())
    val favoriteMoviesState: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.readAllFavorite().collect() { favoriteList ->
                _favoriteMovies.value = favoriteList
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }

}