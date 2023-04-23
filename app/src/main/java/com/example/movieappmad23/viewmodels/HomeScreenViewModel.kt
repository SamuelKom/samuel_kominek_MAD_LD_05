package com.example.movieappmad23.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieListState: StateFlow<List<Movie>> = _movieList.asStateFlow()

    init {
        viewModelScope.launch {

            repository.readAllMovies().collect { movieList ->
                _movieList.value = movieList
                if (movieList.isNotEmpty()) {
                    _movieList.value = movieList
                }
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }


}