package com.stu74523.movie_activity_sarahtsangou_74523

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class MovieState {
    object Loading : MovieState()
    data class Success(val movie: Movie) : MovieState()
    data class Error(val message: String) : MovieState()

}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Success(val movieList: List<Movie>) : MovieListState()
    data class Error(val message: String) : MovieListState()

}

sealed class UpdateMovieState {
    object Loading : UpdateMovieState()
    object Success: UpdateMovieState()
    data class Error(val message: String) : UpdateMovieState()

}



class MovieViewModel : ViewModel() {
    private val _movieState = MutableStateFlow<MovieState>(MovieState.Loading)
    private val _movieListState = MutableStateFlow<MovieListState>(MovieListState.Loading)
    private val _updateMovieState = MutableStateFlow<UpdateMovieState>(UpdateMovieState.Loading)

    val movieState: StateFlow<MovieState> = _movieState
    val movieListState: StateFlow<MovieListState> = _movieListState
    val updateMovieState:StateFlow<UpdateMovieState> = _updateMovieState

    val db = Firebase.firestore

    fun fetchMovieListData(page: Int = 1, init:Boolean = false) {
        viewModelScope.launch {
            _movieListState.value = MovieListState.Loading
            try {
                var MovieList: MutableList<Movie> = mutableListOf()


                db.collection("movies")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            try {
                                val movie:Movie = document.toObject()
                                movie.movieId = document.id
                                if(init) {
                                    movie.seats_remaining = (1..15).random()
                                    movie.seats_selected = 0
                                    updateMovieData(movie)
                                }
                                MovieList.add(movie)
                            }
                            catch (e: Exception)
                            {
                                _movieListState.value =
                                    MovieListState.Error("Failed to fetch List of movies.: \n${e.message}")
                            }
                        }
                    }
                    .await()


                _movieListState.value = MovieListState.Success(MovieList)
            } catch (e: Exception) {
                _movieListState.value =
                    MovieListState.Error("Failed to fetch List of movies.: \n${e.message}")
            }
        }
    }

    fun fetchMovieData(movieId: String) {
        _movieState.value = MovieState.Loading

        viewModelScope.launch {
            try {
                val docRef = db.collection("movies").document(movieId)

                docRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        val movie:Movie = documentSnapshot.toObject<Movie>()!!
                        movie.movieId = documentSnapshot.id

                        _movieState.value = MovieState.Success(movie)
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                    }
                    .await()
            } catch (e: Exception) {
                _movieState.value =
                    MovieState.Error("Failed to fetch _movieState. : \n${e.message}")
            }
        }
    }

    fun updateMovieData(movie:Movie)
    {
        viewModelScope.launch {
            //_movieState.value = MovieState.Loading

            try{
                val docRef = db.collection("movies").document(movie.movieId)

                docRef.update("seats_remaining", movie.seats_remaining)
                    .addOnSuccessListener {
                        _movieState.value = MovieState.Success(movie)
                    }
                    .addOnFailureListener {
                        _movieState.value = MovieState.Error("Failed to update seats_remaining for Movie (id: ${movie.movieId}): \n ${it.message}")
                    }
                    .await()

                docRef.update("seats_selected", movie.seats_selected)
                    .addOnSuccessListener {
                        _movieState.value = MovieState.Success(movie)
                    }
                    .addOnFailureListener {
                        _movieState.value = MovieState.Error("Failed to update seats_selected for Movie (id: ${movie.movieId}): \n ${it.message}")
                    }
                    .await()

            } catch (e:Exception) {
                _movieState.value = MovieState.Error("Failed to Update Movie(id: ${movie.movieId}): \n${e.message}")
            }
        }
    }
}