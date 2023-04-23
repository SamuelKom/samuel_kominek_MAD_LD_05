package com.example.movieappmad23.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movie = MutableStateFlow(Movie())
    val movie: Movie
        get() = _movie.value

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }

    fun getMovie(id: String): Movie{
        viewModelScope.launch {
            repository.getMovieById(id).collect { movie ->
                    _movie.value = movie
            }
        }
        return movie
    }
}